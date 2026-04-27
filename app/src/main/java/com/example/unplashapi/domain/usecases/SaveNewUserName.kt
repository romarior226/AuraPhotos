package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.repo.AuthRepository
import javax.inject.Inject

class SaveNewUserName @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(newUserName: String) {
        repository.updateCurrentUser(newUserName)
    }
}