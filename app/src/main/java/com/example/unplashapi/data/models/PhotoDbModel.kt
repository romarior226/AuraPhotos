package com.example.unplashapi.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoDbModel(
    @PrimaryKey
    val id: String,
    val authorName: String,
    val authorUserName: String,
    val authorAvatar: String,
    val imageUrl: String,
    val location: String? = null,
    val isFavourite: Boolean = false
)