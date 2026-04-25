package com.example.unplashapi.domain.repo

import com.example.unplashapi.domain.models.Token

interface AuthRepository {
    suspend fun getToken(code: String): Token

    suspend fun getSavedToken(): String?

    suspend fun getCurrentUsername(): String?

}