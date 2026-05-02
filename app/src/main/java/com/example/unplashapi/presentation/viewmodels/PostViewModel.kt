package com.example.unplashapi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.models.SimplePhoto
import com.example.unplashapi.domain.models.UserDetail
import com.example.unplashapi.domain.usecases.AddFavouriteUseCase
import com.example.unplashapi.domain.usecases.DeleteFavouriteUseCase
import com.example.unplashapi.domain.usecases.GetListFavouritePostUseCase
import com.example.unplashapi.domain.usecases.GetListPostUseCase
import com.example.unplashapi.domain.usecases.GetUserUseCase
import com.example.unplashapi.domain.usecases.GetUsersPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    val getListPostsUseCase: GetListPostUseCase,
    val getListFavouritePostUseCase: GetListFavouritePostUseCase,
    val addFavouriteUseCase: AddFavouriteUseCase,
    val deleteFavouriteUseCase: DeleteFavouriteUseCase,
    val getUserUseCase: GetUserUseCase,
    val getUsersPhotoUseCase: GetUsersPhotoUseCase
) : ViewModel() {


    val favouriteIds = getListFavouritePostUseCase()
        .map { list ->
            list.map {
                it.id
            }.toSet()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    private val _currentUser = MutableStateFlow<UserDetail?>(null)
    val currentUser: StateFlow<UserDetail?> = _currentUser

    private val _usersPhotos = MutableStateFlow<List<SimplePhoto>>(emptyList())
    val usersPhotos: StateFlow<List<SimplePhoto>> = _usersPhotos


    private val _remotePosts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = combine(_remotePosts, favouriteIds) { remote, favouriteIds ->
        remote.map {
            it.copy(isFavourite = it.id in favouriteIds)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadPosts(1)


    }


    fun loadUser(id: String) {
        viewModelScope.launch {
            try {
                _currentUser.value = null
                _usersPhotos.value = emptyList()
                val user = getUserUseCase(id)
                _currentUser.value = user
                loadUsersPhoto(user.username)
            } catch (e: Exception) {
                _error.value = e.message
            }

        }
    }

    fun loadNextPage(userName: String, page: Int) {
        viewModelScope.launch {
            try {
                _usersPhotos.value += getUsersPhotoUseCase(userName, page)
            } catch (e: Exception) {
                _error.value = e.message
            }

        }

    }

    fun loadMorePosts(page: Int) {
        viewModelScope.launch {
            try {
                _remotePosts.value += getListPostsUseCase(page)
            } catch (e: Exception) {
                _error.value = e.message
            }

        }
    }

    fun loadUsersPhoto(userName: String) {
        viewModelScope.launch {
            try {
                _usersPhotos.value = getUsersPhotoUseCase(userName)
            } catch (e: Exception) {
                throw RuntimeException(e.message)
            }

        }

    }

    fun toggleFavourite(id: String) {
        val currentPost = posts.value.find { it.id == id } ?: return
        viewModelScope.launch {
            if (currentPost.isFavourite) {
                deleteFavouriteUseCase(id)
            } else {
                addFavouriteUseCase(currentPost.copy(isFavourite = true))
            }
        }
    }

    fun loadPosts(page: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _remotePosts.value = getListPostsUseCase(page)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}