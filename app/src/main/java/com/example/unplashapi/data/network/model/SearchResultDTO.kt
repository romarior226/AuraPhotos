package com.example.unplashapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDTO(
    @SerialName("results") val results: List<SimplePhotoDTO>
)
