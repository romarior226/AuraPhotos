package com.example.unplashapi.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplashapi.domain.usecases.GetLoggedUserUseCase
import com.example.unplashapi.domain.usecases.GetSavedTokenUseCase
import com.example.unplashapi.domain.usecases.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val getTokenUseCase: GetTokenUseCase,
    val getSavedTokenUseCase: GetSavedTokenUseCase,
    val getLoggedUserUseCase: GetLoggedUserUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<String?>(null)

    val user: StateFlow<String?> = _user
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    init {
        viewModelScope.launch {
            _token.value = getSavedTokenUseCase()
            Log.d("AUTH_LOG", "token: ${_token.value}")
            if (_token.value != null) {
                _user.value = getLoggedUserUseCase()
                Log.d("AUTH_LOG", "user: ${_user.value}")
            }

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