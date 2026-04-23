package com.example.unplashapi.domain.repo

import com.example.unplashapi.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface FavouritePostRepository {
    fun getFavouritePosts(): Flow<List<Post>>

    suspend fun addFavouritePost(post: Post): Long

    suspend fun deleteFavouritePost(id: String)


}