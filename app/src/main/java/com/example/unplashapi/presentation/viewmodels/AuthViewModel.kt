package com.example.unplashapi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplashapi.domain.models.UserDetail
import com.example.unplashapi.domain.usecases.ChangeUsersInfoUseCase
import com.example.unplashapi.domain.usecases.GetLoggedUserUseCase
import com.example.unplashapi.domain.usecases.GetSavedTokenUseCase
import com.example.unplashapi.domain.usecases.GetTokenUseCase
import com.example.unplashapi.domain.usecases.GetUserUseCase
import com.example.unplashapi.domain.usecases.SaveNewUserName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

data class EditProfileUiState(
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val location: String = "",
    val bio: String = "",
    val instagramUsername: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false
) {
    val isDataValid: Boolean get() = username.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    val getTokenUseCase: GetTokenUseCase,
    val getSavedTokenUseCase: GetSavedTokenUseCase,
    val getLoggedUserUseCase: GetLoggedUserUseCase,
    val changeUsersInfoUseCase: ChangeUsersInfoUseCase,
    val getUserUseCase: GetUserUseCase,
    val saveNewUserName: SaveNewUserName
) : ViewModel() {

    private val _user = MutableStateFlow<String?>(null)

    val user: StateFlow<String?> = _user
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val _state = MutableStateFlow(
        EditProfileUiState(

        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _token.value = getSavedTokenUseCase()
            if (_token.value != null) {
                val loggerUser = getLoggedUserUseCase().username
                _user.value = loggerUser
                try {
                    val userDetail = getLoggedUserUseCase()
                    prepareEditState(userDetail)
                } catch (e: Exception) {

                }
            }


        }
    }

    fun prepareEditState(user: UserDetail) {
        _state.update {
            it.copy(
                username = user.username,
                firstName = user.firstName ?: user.name.split(" ").firstOrNull() ?: "",
                lastName = user.lastName ?: user.name.split(" ").lastOrNull() ?: "",
                email = user.email ?: "",
                location = user.location ?: "",
                bio = user.bio ?: "",
                instagramUsername = user.instagramUsername ?: ""
            )
        }
    }

    fun changeUsersData(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        location: String,
        bio: String,
        instagramUsername: String
    ) {
        viewModelScope.launch {
            saveNewUserName(username)
            changeUsersInfoUseCase(
                username = username,
                firstName = firstName,
                lastName = lastName,
                email = email,
                location = location,
                bio = bio,
                instagramUsername = instagramUsername,
            )
        }
    }


    val isAuthorized: StateFlow<Boolean> = _token.map {
        it != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun loadToken(code: String) {
        viewModelScope.launch {
            _token.value = getTokenUseCase(
                code = code
            ).accessToken
        }
    }


}