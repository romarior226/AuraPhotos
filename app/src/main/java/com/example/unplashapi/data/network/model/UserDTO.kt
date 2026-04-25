package com.example.unplashapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("profile_image") val profileImage: ProfileImageDTO,
    @SerialName("name") val name: String,
    @SerialName("location") val location: String? = null
)

@Serializable
data class  ProfileImageDTO(
    @SerialName("medium") val medium: String
)
@Serializable
data class  ProfileImageLargeDTO(
    @SerialName("large") val large: String
)
