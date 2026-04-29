package com.example.unplashapi.data.network.repo

import com.example.unplashapi.data.mapper.toModel
import com.example.unplashapi.data.network.remote.UnsplashApi
import com.example.unplashapi.domain.models.DetailPhoto
import com.example.unplashapi.domain.models.PhotoStatistics
import com.example.unplashapi.domain.repo.DetailedPostRepository
import javax.inject.Inject

class DetailedPostRepositoryImpl @Inject constructor(
    private val api: UnsplashApi
) : DetailedPostRepository {
    override suspend fun getPost(id: String): DetailPhoto {
        return api.getPhoto(id).toModel()
    }

    override suspend fun getPhotoStatistics(id: String): PhotoStatistics {
        return api.getPhotoStatistics(id).toModel()
    }
}