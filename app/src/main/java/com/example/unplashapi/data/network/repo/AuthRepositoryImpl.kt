package com.example.unplashapi.data.network.repo

import com.example.unplashapi.AppDataStore
import com.example.unplashapi.BuildConfig
import com.example.unplashapi.data.mapper.toModel
import com.example.unplashapi.data.network.remote.Unsplash
import com.example.unplashapi.domain.models.Token
import com.example.unplashapi.domain.repo.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val api: Unsplash,
    val authDataStore: AppDataStore
) : AuthRepository {
    override suspend fun getToken(code: String): Token {
        val token = api.postUser(
            clientId = BuildConfig.UNSPLASH_KEY,
            clientSecret = BuildConfig.SECRET_KEY,
            redirectUri = "unsplashapp://callback",
            code = code,
            authorizationCode = "authorization_code"
        ).toModel()
        authDataStore.saveToken(token.accessToken)
        authDataStore.saveUsername(token.username)
        return token
    }

    override suspend fun getSavedToken(): String? {
        return authDataStore.accessToken.first()
    }
    override suspend fun getCurrentUsername(): String? {
        return authDataStore.userName.first()
    }
}