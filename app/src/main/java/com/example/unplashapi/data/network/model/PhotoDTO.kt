package com.example.unplashapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimplePhotoDTO(
    @SerialName("id") val id: String,
    @SerialName("urls") val urls: PhotoUrlDTO,
)
