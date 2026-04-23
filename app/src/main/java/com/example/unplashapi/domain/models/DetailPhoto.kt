package com.example.unplashapi.domain.models

data class DetailPhoto(
    val id: String,
    val createdAt: String,
    val downloads: Int?,
    val urls: String,
    val location: String?,
    val authorName: String,
    val authorUserName: String,
    val authorAvatar: String,
    val tags: List<String>,
    val link: String,
)
