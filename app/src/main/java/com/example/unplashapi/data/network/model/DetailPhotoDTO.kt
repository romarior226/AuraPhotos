package com.example.unplashapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DetailPhotoDTO(
    @SerialName("id") val id: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("downloads") val downloads: Int?,
    @SerialName("urls") val urls: PhotoUrlDTO,
    @SerialName("location") val location: LocationDTO?,
    @SerialName("user") val user: UserDTO,
    @SerialName("tags") val tags: List<TagDTO>,
    @SerialName("links") val links: LinksDTO,
)

@Serializable
data class LocationDTO(
    @SerialName("city") val city: String?,
    @SerialName("country") val country: String?,
)

@Serializable
data class TagDTO(
    @SerialName("title") val title: String
)

@Serializable
data class LinksDTO(
    @SerialName("html") val html: String
)