package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.repo.FavouritePostRepository
import javax.inject.Inject

class DeleteFavouriteUseCase @Inject constructor(private val repository: FavouritePostRepository) {
    suspend operator fun invoke(id: String) {
        repository.deleteFavouritePost(id)
    }
}