package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.SimplePhoto
import com.example.unplashapi.domain.repo.PostRepository
import javax.inject.Inject

class GetUsersPhotoUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(userName: String, page: Int = 1): List<SimplePhoto> {
        return repository.getUsersPhoto(userName, page)
    }
}