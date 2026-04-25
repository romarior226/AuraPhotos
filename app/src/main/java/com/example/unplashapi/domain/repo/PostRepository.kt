package com.example.unplashapi.domain.repo

import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.models.SimplePhoto
import com.example.unplashapi.domain.models.UserDetail

interface PostRepository {

    suspend fun getPosts(page: Int): List<Post>

    suspend fun getUser(id: String): UserDetail

    suspend fun getUsersPhoto(userName: String, page: Int = 1): List<SimplePhoto>

}