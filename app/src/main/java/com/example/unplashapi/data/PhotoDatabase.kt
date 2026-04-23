package com.example.unplashapi.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unplashapi.data.dao.PhotoDao
import com.example.unplashapi.data.models.PhotoDbModel

@Database(entities = [PhotoDbModel::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}