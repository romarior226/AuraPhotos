package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.repo.PostRepository
import javax.inject.Inject

class TriggerPostUseCase@Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(id: String) {
        repository.triggerPost(id)
    }
}