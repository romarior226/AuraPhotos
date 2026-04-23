package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.DetailPhoto
import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.repo.DetailedPostRepository
import javax.inject.Inject

class GetDetailedPhotoUseCase @Inject constructor(private val detailedPostRepository: DetailedPostRepository) {
    suspend operator fun invoke(id: String): DetailPhoto {
        return detailedPostRepository.getPost(id)
    }
}