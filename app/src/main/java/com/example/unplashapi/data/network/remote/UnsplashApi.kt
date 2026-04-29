    package com.example.unplashapi.data.network.remote

    import com.example.unplashapi.data.network.model.DetailPhotoDTO
    import com.example.unplashapi.data.network.model.DetailPhotoStatisticDTO
    import com.example.unplashapi.data.network.model.PostDTO
    import com.example.unplashapi.data.network.model.SimplePhotoDTO
    import com.example.unplashapi.data.network.model.UserDetailDTO
    import retrofit2.Response
    import retrofit2.http.GET
    import retrofit2.http.Header
    import retrofit2.http.PUT
    import retrofit2.http.Path
    import retrofit2.http.Query

    interface UnsplashApi {

        @GET("photos/{id}/download")
        suspend fun triggerPost(
            @Path("id") id: String
        ): Response<Unit>

        @GET("photos")
        suspend fun getPosts(
            @Query("page") page: Int = 1,
            @Query("per_page") perPage: Int = 10
        ): List<PostDTO>

        @GET("photos/{id}")
        suspend fun getPhoto(
            @Path("id") id: String
        ): DetailPhotoDTO

        @GET("photos/{id}/statistics")
        suspend fun getPhotoStatistics(
            @Path("id") id: String
        ): DetailPhotoStatisticDTO

        @GET("users/{username}/photos")
        suspend fun getUserPhotos(
            @Path("username") userName: String,
            @Query("page") page: Int = 1,
            @Query("per_page") perPage: Int = 10,
            @Query("stats") stats: Boolean = true
        ): List<SimplePhotoDTO>

        @GET("users/{username}")
        suspend fun getUser(
            @Path("username") username: String
        ): UserDetailDTO

        @PUT("me")
        suspend fun changeUserData(
            @Header("Authorization") token: String,
            @Query("username") username: String,
            @Query("first_name") firstName: String,
            @Query("last_name") lastName: String,
            @Query("email") email: String,
            @Query("location") location: String,
            @Query("bio") bio: String,
            @Query("instagram_username") instagramUsername: String,
        ): UserDetailDTO

        @GET("me")
        suspend fun getMyUserInfo(
            @Header("Authorization") token: String,
        ): UserDetailDTO
    }