package com.example.unplashapi.domain.usecases

import com.example.unplashapi.domain.repo.AuthRepository
import javax.inject.Inject

class ChangeUsersInfoUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        location: String,
        bio: String,
        instagramUsername: String,
    ) {
        repository.changeUserData(
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            location = location,
            bio = bio,
            instagramUsername = instagramUsername,
        )
    }
}