package com.example.unplashapi.navgition

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.unplashapi.AppDataStore
import com.example.unplashapi.BuildConfig
import com.example.unplashapi.domain.models.ResultState
import com.example.unplashapi.presentation.screens.AuthorizationScreen
import com.example.unplashapi.presentation.screens.DetailedScreen
import com.example.unplashapi.presentation.screens.PhotoFeedScreen
import com.example.unplashapi.presentation.screens.ProfileScreen
import com.example.unplashapi.presentation.screens.SearchScreen
import com.example.unplashapi.presentation.viewmodels.AuthViewModel
import com.example.unplashapi.presentation.viewmodels.DetailViewModel
import com.example.unplashapi.presentation.viewmodels.PostViewModel
import com.example.unplashapi.presentation.viewmodels.SearchViewModel

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    detailViewModel: DetailViewModel,
    postViewModel: PostViewModel,
    authViewModel: AuthViewModel,
    searchViewModel: SearchViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthScreen.route
    ) {
        composable(Screen.PostsScreen.route) {
            PhotoFeedScreen(
                viewModel = postViewModel,
                onPostClickListener = {
                    val route = Screen.DetailedPostScreen.getRouteWithArgs(it.id)
                    navHostController.navigate(route)
                },
                onAvatarClickListener = {
                    val route = Screen.UserProfileScreen.getRouteWithArgs(it)
                    navHostController.navigate(route)
                },
            )

        }
        composable(Screen.SearchScreen.route) {
            val searchState by searchViewModel.searchState.collectAsState()
            SearchScreen(
                searchState  = searchState,
                searchQuery = {
                    searchViewModel.    onQueryChanged(it)
                },
                loadMorePhoto = {
                    searchViewModel.loadMoreSearchedPhotos(it)
                },
                onPhotoClicked = {
                    val route = Screen.DetailedPostScreen.getRouteWithArgs(it)
                    navHostController.navigate(route)
                }
            )
        }

    composable(Screen.FavouriteScreen.route) {

    }
    composable(Screen.AuthScreen.route) {
        val isAuthorized by authViewModel.isAuthorized.collectAsState()
        val context = LocalContext.current
        LaunchedEffect(isAuthorized) {
            if (isAuthorized) {
                navHostController.navigate(Screen.PostsScreen.route) {
                    popUpTo(Screen.AuthScreen.route) { inclusive = true }
                }
            }
        }
        AuthorizationScreen {
            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(
                context, Uri.parse(
                    "https://unsplash.com/oauth/authorize?client_id=${BuildConfig.UNSPLASH_KEY}&redirect_uri=unsplashapp://callback&response_type=code&scope=public+read_user+write_user+write_likes"
                )
            )
        }
    }

    composable(
        route = Screen.DetailedPostScreen.route,
        arguments = listOf(navArgument("postId") { type = NavType.StringType })
    ) { navBackStackEntry ->
        val postId = navBackStackEntry.arguments?.getString("postId")
        val photoState by detailViewModel.photoState.collectAsState()

        LaunchedEffect(postId) {
            if (postId != null) detailViewModel.loadPhoto(postId)
        }
        val collection by postViewModel.usersPhotos.collectAsState()
        val statistics by detailViewModel.photoStatistics.collectAsState()
        when (val state = photoState) {
            is ResultState.Error -> Text(state.message)
            ResultState.Loading -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is ResultState.Success -> DetailedScreen(
                detailPhoto = state.data,
                onBackPressed = {
                    navHostController.popBackStack()
                },
                onAvatarClickListener = { userId ->
                    val route = Screen.UserProfileScreen.getRouteWithArgs(userId)
                    navHostController.navigate(route)
                },
                collection = collection.filter { photoFromGrid ->
                    photoFromGrid.id != state.data.id
                },
                loadMorePhoto = { page ->
                    postViewModel.loadNextPage(state.data.authorUserName, page)
                },
                onPhotoClicked = { photoId ->
                    val route = Screen.DetailedPostScreen.getRouteWithArgs(photoId)
                    navHostController.navigate(route)
                },
                photoStatistics = statistics
                    ?: throw IllegalArgumentException("There are no Statistics"),
            )
        }

    }

    composable(
        route = Screen.UserProfileScreen.route,
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) { navBackStackEntry ->
        val userId = navBackStackEntry.arguments?.getString("userId")
        val user by postViewModel.currentUser.collectAsState()
        val collection by postViewModel.usersPhotos.collectAsState()
        LaunchedEffect(userId) {
            if (userId != null) {
                postViewModel.loadUser(userId)
            }

        }
        val currentUser by authViewModel.user.collectAsState()
        val editState by authViewModel.state.collectAsState()
        user?.let {
            ProfileScreen(
                profile = it, collection = collection,
                loadMorePhoto = { page ->
                    postViewModel.loadNextPage(it.username, page)
                },
                onBackPressed = { navHostController.popBackStack() },
                onPhotoClicked = { photoId ->
                    val route = Screen.DetailedPostScreen.getRouteWithArgs(photoId)
                    navHostController.navigate(route)
                },
                isEditable = currentUser == it.username,
                onSaveProfile = { username, firstName, lastName, email, location, bio, instagramUsername ->
                    authViewModel.changeUsersData(
                        username,
                        firstName,
                        lastName,
                        email,
                        location,
                        bio,
                        instagramUsername
                    )
                },
                editState = editState,
            )
        }
    }
}
}