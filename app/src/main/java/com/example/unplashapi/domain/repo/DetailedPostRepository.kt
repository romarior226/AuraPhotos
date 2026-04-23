package com.example.unplashapi.domain.repo

import com.example.unplashapi.domain.models.DetailPhoto
import com.example.unplashapi.domain.models.Post

interface DetailedPostRepository {
    suspend fun getPost(id: String): DetailPhoto
}