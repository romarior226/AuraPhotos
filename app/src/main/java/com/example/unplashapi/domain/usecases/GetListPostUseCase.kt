package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.repo.PostRepository
import javax.inject.Inject

class GetListPostUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(page: Int): List<Post> {
        return repository.getPosts(page)
    }
}