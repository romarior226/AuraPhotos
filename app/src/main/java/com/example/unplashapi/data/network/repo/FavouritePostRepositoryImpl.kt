package com.example.unplashapi.data.network.repo

import com.example.unplashapi.data.dao.PhotoDao
import com.example.unplashapi.data.mapper.toDbModel
import com.example.unplashapi.data.mapper.toModel
import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.domain.repo.FavouritePostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritePostRepositoryImpl @Inject constructor(val photoDao: PhotoDao) :
    FavouritePostRepository {


    override  fun getFavouritePosts(): Flow<List<Post>> {
        return photoDao.getAllPhotos().map { it ->
            it.map {photoDbModel ->
                photoDbModel.toModel()
            }
        }
    }

    override suspend fun addFavouritePost(post: Post): Long {
        return photoDao.insertPhoto(post.toDbModel())
    }

    override suspend fun deleteFavouritePost(id: String) {
        photoDao.deletePhoto(id)
    }
}