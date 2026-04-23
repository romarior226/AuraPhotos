package com.example.unplashapi.navgition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.unplashapi.presentation.screens.DetailedScreen
import com.example.unplashapi.presentation.screens.PhotoFeedScreen
import com.example.unplashapi.presentation.viewmodels.DetailViewModel
import com.example.unplashapi.presentation.viewmodels.PostViewModel

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    detailViewModel: DetailViewModel,
    postViewModel: PostViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PostsScreen.route
    ) {
        composable(Screen.PostsScreen.route) {
            PhotoFeedScreen(
                viewModel = postViewModel,
                onPostClickListener = {
                    val route = Screen.DetailedPostScreen.getRouteWithArgs(it.id)
                    navHostController.navigate(route)
                }
            )

        }
        composable(Screen.FavouriteScreen.route) {
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
                DetailedScreen(it) {
                    navHostController.popBackStack()
                }
            }
        }
    }
}