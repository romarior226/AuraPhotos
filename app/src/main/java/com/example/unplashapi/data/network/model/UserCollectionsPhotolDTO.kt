package com.example.unplashapi.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Serializable
//    data class UserPhotoDTO(
//    @SerialName("id") val id: String,
//    @SerialName("user") val user : UserDetailDTO,
//    @SerialName("name") val name: String,
//    @SerialName("location") val location: String? = null,
//    @SerialName("statistics") val statistics: StatisticsDTO? = null,
//    @SerialName("urls") val urls: PhotoUrlDTO? = null
//)

//@Serializable
//data class StatisticsDTO(
//    @SerialName("downloads") val downloads: DownloadsDTO,
//    @SerialName("views") val views: ViewsDTO,
//)

@Serializable
data class DownloadsDTO(
    @SerialName("total") val total: Int,
)

@Serializable
data class ViewsDTO(
    @SerialName("total") val total: Int,
)

@Serializable
data class UserDetailDTO(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("profile_image") val profileImage: ProfileImageLargeDTO,
    @SerialName("name") val name: String,
    @SerialName("bio") val bio: String?,
    @SerialName("total_collections") val totalCollections: Int,
    @SerialName("total_photos") val totalPhotos: Int,
    @SerialName("location") val location: String? = null,

    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("instagram_username") val instagramUsername: String? = null
)
