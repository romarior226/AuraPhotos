package com.example.unplashapi.data.network.repo

import com.example.unplashapi.data.mapper.toModel
import com.example.unplashapi.data.network.remote.UnsplashApi
import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.repo.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(val unsplashApi: UnsplashApi) : PostRepository {


    override suspend fun getPosts(page: Int): List<Post> {
        return unsplashApi.getPhotos(page).map {
            it.toModel()
        }
    }

}