package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.UserDetail
import com.example.unplashapi.domain.repo.AuthRepository
import javax.inject.Inject

class GetLoggedUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): UserDetail  {
        return repository.getCurrentUsername()
    }
}