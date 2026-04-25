package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.UserDetail
import com.example.unplashapi.domain.repo.PostRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(id: String): UserDetail {
        return repository.getUser(id)
    }
}