package com.example.unplashapi.data.mapper

import com.example.unplashapi.data.models.PhotoDbModel
import com.example.unplashapi.data.network.model.DetailPhotoDTO
import com.example.unplashapi.data.network.model.PhotoDTO
import com.example.unplashapi.domain.models.DetailPhoto
import com.example.unplashapi.domain.models.Post


fun PhotoDTO.toModel(): Post {
    return Post(
        id = this.id,
        authorName = this.user.name,
        authorUserName = this.user.username,
        authorAvatar = this.user.profileImage.medium,
        imageUrl = this.urls.fullUrl,
        location = this.user.location,
    )
}

fun PhotoDbModel.toModel(): Post {
    return Post(
        id = this.id,
        authorName = this.authorName,
        authorUserName = this.authorUserName,
        authorAvatar = this.authorAvatar,
        imageUrl = this.imageUrl,
        location = this.location,
        isFavourite = this.isFavourite
    )
}

fun Post.toDbModel(): PhotoDbModel {
    return PhotoDbModel(
        id = this.id,
        authorName = this.authorName,
        authorUserName = this.authorUserName,
        authorAvatar = this.authorAvatar,
        imageUrl = this.imageUrl,
        location = this.location,
        isFavourite = this.isFavourite
    )
}

fun DetailPhotoDTO.toModel(): DetailPhoto {
    return DetailPhoto(
        id = this.id,
        createdAt = this.createdAt,
        downloads = this.downloads,
        urls = this.urls.fullUrl,
        location = listOfNotNull(this.location?.city, this.location?.country).joinToString(", "),
        authorName = this.user.name,
        authorUserName = this.user.username,
        authorAvatar = this.user.profileImage.medium,
        tags = this.tags.map { it.title },
        link = this.links.html,
    )
}


