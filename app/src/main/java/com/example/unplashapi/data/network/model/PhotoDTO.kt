package com.example.unplashapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PhotoDTO(
    @SerialName("id") val id: String,
    @SerialName("urls") val urls: PhotoUrl,
    @SerialName("user") val user: UserDTO,

)


@Serializable
data class PhotoUrl(
    @SerialName("full") val fullUrl: String
)
