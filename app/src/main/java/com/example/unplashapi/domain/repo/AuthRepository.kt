    package com.example.unplashapi.domain.repo

    import com.example.unplashapi.domain.models.Token
    import com.example.unplashapi.domain.models.UserDetail

    interface AuthRepository {
        suspend fun getToken(code: String): Token

        suspend fun getSavedToken(): String?

        suspend fun getCurrentUsername(): UserDetail

        suspend fun updateCurrentUser(newName: String)

        suspend fun changeUserData(
            username: String,
            firstName: String,
            lastName: String,
            email: String,
            location: String,
            bio: String,
            instagramUsername: String,
        )

    }