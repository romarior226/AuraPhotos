package com.example.unplashapi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.usecases.AddFavouriteUseCase
import com.example.unplashapi.domain.usecases.DeleteFavouriteUseCase
import com.example.unplashapi.domain.usecases.GetListFavouritePostUseCase
import com.example.unplashapi.domain.usecases.GetListPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    val getListPostsUseCase: GetListPostUseCase,
    val getListFavouritePostUseCase: GetListFavouritePostUseCase,
    val addFavouriteUseCase: AddFavouriteUseCase,
    val deleteFavouriteUseCase: DeleteFavouriteUseCase
) : ViewModel() {

    val favouritePost = getListFavouritePostUseCase()

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _posts = MutableStateFlow<List<Post>>(emptyList())

    val posts: StateFlow<List<Post>> = _posts

    init {
        viewModelScope.launch {
            _posts.value.map {
                getListFavouritePostUseCase().collect { favourites ->
                    val favouriteIds = favourites.map { it.id }.toSet()
                    _posts.value = _posts.value.map { post ->
                        post.copy(isFavourite = post.id in favouriteIds)
                    }
                }
            }
        }
    }

    fun toggleFavourite(id: String) {
        val currentPost = _posts.value.find { it.id == id } ?: return
        viewModelScope.launch {
            if (currentPost.isFavourite) {
                deleteFavouriteUseCase(id)
            } else {
                addFavouriteUseCase(currentPost.copy(isFavourite = true))
            }
            _posts.value = _posts.value.map {
                if (it.id == id) it.copy(isFavourite = !it.isFavourite) else it
            }
        }
    }

    fun addFavourite(post: Post) {
        viewModelScope.launch {
            addFavouriteUseCase(post)
        }
    }

    fun deleteFavourite(id: String) {
        viewModelScope.launch {
            deleteFavouriteUseCase(id)
        }
    }

    fun loadPosts(page: Int) {
        viewModelScope.launch {
            val favouriteIds = getListFavouritePostUseCase()
                .first()
                .map { it.id }
                .toSet()
            _posts.value = getListPostsUseCase(page).map { post ->
                post.copy(isFavourite = post.id in favouriteIds)
            }
        }
    }

}