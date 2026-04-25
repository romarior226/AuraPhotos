package com.example.unplashapi.di

import com.example.unplashapi.data.network.repo.AuthRepositoryImpl
import com.example.unplashapi.data.network.repo.DetailedPostRepositoryImpl
import com.example.unplashapi.data.network.repo.FavouritePostRepositoryImpl
import com.example.unplashapi.data.network.repo.PostRepositoryImpl
import com.example.unplashapi.domain.repo.AuthRepository
import com.example.unplashapi.domain.repo.DetailedPostRepository
import com.example.unplashapi.domain.repo.FavouritePostRepository
import com.example.unplashapi.domain.repo.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository

    @Binds
    abstract fun bindFavouritePostRepository(
        favouritePostRepository: FavouritePostRepositoryImpl
    ): FavouritePostRepository

    @Binds
    abstract fun bindDetailedPostRepository(
        detailedPostRepositoryImpl: DetailedPostRepositoryImpl
    ): DetailedPostRepository

    @Binds
    abstract fun bindAuthRepositoryImpl(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}