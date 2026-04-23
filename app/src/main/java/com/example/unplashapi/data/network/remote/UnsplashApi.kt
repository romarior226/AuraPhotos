package com.example.unplashapi.data.network.remote

import com.example.unplashapi.data.network.model.DetailPhotoDTO
import com.example.unplashapi.data.network.model.PhotoDTO
import com.example.unplashapi.data.network.model.UserDTO
import com.example.unplashapi.domain.models.DetailPhoto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): List<PhotoDTO>

    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id : String
    ): DetailPhotoDTO


    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") id : String
    ): UserDTO
}