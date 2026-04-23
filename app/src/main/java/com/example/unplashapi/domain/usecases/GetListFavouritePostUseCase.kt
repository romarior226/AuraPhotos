    package com.example.unplashapi.domain.usecases

    import com.example.unplashapi.domain.models.Post
    import com.example.unplashapi.domain.repo.FavouritePostRepository
    import com.example.unplashapi.domain.repo.PostRepository
    import kotlinx.coroutines.flow.Flow
    import javax.inject.Inject

    class GetListFavouritePostUseCase @Inject constructor(private val repository: FavouritePostRepository) {
        operator fun invoke(): Flow<List<Post>> {
            return repository.getFavouritePosts()
        }
    }