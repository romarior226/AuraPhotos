package com.example.unplashapi.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.unplashapi.data.models.PhotoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): Flow<List<PhotoDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(ingredient: PhotoDbModel): Long

    @Update
    suspend fun updatePhoto(ingredient: PhotoDbModel)

    @Query("DELETE FROM photos WHERE id = :id")
    suspend fun deletePhoto(id: String)

}