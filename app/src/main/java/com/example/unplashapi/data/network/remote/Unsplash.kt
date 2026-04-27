package com.example.unplashapi.data.network.remote

import com.example.unplashapi.data.network.model.TokenDTO
import com.example.unplashapi.data.network.model.UserDetailDTO
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface Unsplash {
    @FormUrlEncoded
    @POST("token")
    suspend fun postUser(
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("redirect_uri") redirectUri: String,
            @Field("code") code: String,
            @Field("grant_type") authorizationCode: String,
    ): TokenDTO




  //  @FormUrlEncoded

}