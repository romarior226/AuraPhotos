package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.Token
import com.example.unplashapi.domain.repo.AuthRepository
import javax.inject.Inject

class GetSavedTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): String? {
        return repository.getSavedToken()
    }
}