package com.example.unplashapi.domain.models

data class UserDetail(
    val id: String,
    val username: String,
    val profileImage: String,
    val name: String,
    val bio: String?,
    val totalCollections: Int,
    val totalPhotos: Int,
    val location: String? = null
)
