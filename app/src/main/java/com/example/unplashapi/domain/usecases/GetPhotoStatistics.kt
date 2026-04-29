package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.PhotoStatistics
import com.example.unplashapi.domain.repo.DetailedPostRepository
import javax.inject.Inject

class GetPhotoStatistics @Inject constructor(private val repository: DetailedPostRepository) {
    suspend operator fun invoke(id: String): PhotoStatistics {
        return repository.getPhotoStatistics(id)
    }
}