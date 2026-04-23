package com.example.unplashapi.domain.repo

import com.example.unplashapi.domain.models.Post

interface PostRepository {

    suspend fun getPosts(page: Int): List<Post>

}