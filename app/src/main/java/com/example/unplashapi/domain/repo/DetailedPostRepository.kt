package com.example.unplashapi.domain.repo

import com.example.unplashapi.domain.models.DetailPhoto

interface DetailedPostRepository {
    suspend fun getPost(id: String): DetailPhoto
}