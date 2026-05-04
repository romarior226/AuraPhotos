package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.SimplePhoto
import com.example.unplashapi.domain.repo.PostRepository
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(query: String, page: Int = 10): List<SimplePhoto> {
        return repository.searchPhoto(query, page)
    }
}