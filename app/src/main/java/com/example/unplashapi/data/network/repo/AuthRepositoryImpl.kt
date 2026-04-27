package com.example.unplashapi.data.network.repo

import com.example.unplashapi.AppDataStore
import com.example.unplashapi.BuildConfig
import com.example.unplashapi.data.mapper.toModel
import com.example.unplashapi.data.network.remote.Unsplash
import com.example.unplashapi.data.network.remote.UnsplashApi
import com.example.unplashapi.domain.models.Token
import com.example.unplashapi.domain.models.UserDetail
import com.example.unplashapi.domain.repo.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val api: Unsplash,
    val unsplashApi: UnsplashApi,
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

    override suspend fun getCurrentUsername(): UserDetail {
        val token = authDataStore.accessToken.first() ?: throw IllegalArgumentException("WRONG TOKEN")
        return unsplashApi.getMyUserInfo("Bearer $token").toModel()
    }

    override suspend fun updateCurrentUser(newName: String) {
        authDataStore.updateUserName(newName)
    }


    override suspend fun changeUserData(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        location: String,
        bio: String,
        instagramUsername: String
    ) {
        val token = authDataStore.accessToken.first() ?: return
        unsplashApi.changeUserData(
            token = "Bearer $token",
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            location = location,
            bio = bio,
            instagramUsername = instagramUsername
        )
    }
}