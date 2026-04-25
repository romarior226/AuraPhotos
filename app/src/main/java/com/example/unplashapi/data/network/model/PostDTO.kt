package com.example.unplashapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PostDTO(
    @SerialName("id") val id: String,
    @SerialName("urls") val urls: PhotoUrlDTO,
    @SerialName("user") val user: UserDTO,

    )


@Serializable
data class PhotoUrlDTO(
    @SerialName("full") val fullUrl: String
)
