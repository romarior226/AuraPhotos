package com.example.unplashapi.domain.models

data class Post(
    val id: String,
    val authorName: String,
    val authorUserName: String,
    val authorAvatar: String,
    val imageUrl: String,
    val location: String? = null,
    val isFavourite: Boolean = false
)
