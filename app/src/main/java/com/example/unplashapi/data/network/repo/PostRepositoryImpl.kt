package com.example.unplashapi.data.network.repo

import com.example.unplashapi.data.mapper.toModel
import com.example.unplashapi.data.network.remote.UnsplashApi
import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.models.SimplePhoto
import com.example.unplashapi.domain.models.UserDetail
import com.example.unplashapi.domain.repo.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(val unsplashApi: UnsplashApi) : PostRepository {
    override suspend fun getPosts(page: Int): List<Post> {
        return unsplashApi.getPosts(page).map {
            it.toModel()
        }
    }

    override suspend fun getUser(username: String): UserDetail {
        return unsplashApi.getUser(username).toModel()
    }

    override suspend fun getUsersPhoto(userName: String, page: Int): List<SimplePhoto> {
        return unsplashApi.getUserPhotos(
            userName = userName,
            page = page
        ).map {
            it.toModel()
        }
    }

    override suspend fun triggerPost(id: String) {
         unsplashApi.triggerPost(id)
    }

}