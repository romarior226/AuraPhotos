package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.repo.FavouritePostRepository
import javax.inject.Inject

class AddFavouriteUseCase @Inject constructor(private val repository: FavouritePostRepository) {
    suspend operator fun invoke(post: Post): Long {
        return repository.addFavouritePost(post)
    }
}