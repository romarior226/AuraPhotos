package com.example.unplashapi.navgition

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.unplashapi.AppDataStore
import com.example.unplashapi.BuildConfig
import com.example.unplashapi.presentation.screens.AuthorizationScreen
import com.example.unplashapi.presentation.screens.DetailedScreen
import com.example.unplashapi.presentation.screens.PhotoFeedScreen
import com.example.unplashapi.presentation.screens.ProfileScreen
import com.example.unplashapi.presentation.viewmodels.AuthViewModel
import com.example.unplashapi.presentation.viewmodels.DetailViewModel
import com.example.unplashapi.presentation.viewmodels.PostViewModel

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    detailViewModel: DetailViewModel,
    postViewModel: PostViewModel,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthScreen.route
    ) {
        composable(Screen.PostsScreen.route) {
            val currentUser by authViewModel.user.collectAsState()
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
                onProfileClickListener = {
                    val username = currentUser
                    if (username != null) {
                        val route = Screen.UserProfileScreen.getRouteWithArgs(username)
                        navHostController.navigate(route)
                    }


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
            val photo by detailViewModel.photo.collectAsState()

            LaunchedEffect(postId) {
                if (postId != null) detailViewModel.loadPhoto(postId)
            }

            photo?.let {
                DetailedScreen(
                    it,
                    onBackPressed = {
                        navHostController.popBackStack()
                    },
                    onAvatarClickListener = { userId ->
                        val route = Screen.UserProfileScreen.getRouteWithArgs(userId)
                        navHostController.navigate(route)
                    },
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
                    onSaveProfile = { username , firstName , lastName , email , location , bio , instagramUsername->
                        authViewModel.changeUsersData(username,firstName,lastName,email,location,bio,instagramUsername)
                    },
                    editState = editState,
                )
            }
        }
    }
}