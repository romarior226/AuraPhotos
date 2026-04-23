package com.example.unplashapi.di

import android.content.Context
import androidx.room.Room
import com.example.unplashapi.data.PhotoDatabase
import com.example.unplashapi.data.dao.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = PhotoDatabase::class.java,
            name = "photoDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun providesPhotoDao(database: PhotoDatabase): PhotoDao {
        return database.photoDao()

    }
}