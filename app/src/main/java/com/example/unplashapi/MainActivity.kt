package com.example.unplashapi

import UnplashApiTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.unplashapi.navgition.AppNavGraph
import com.example.unplashapi.presentation.screens.PhotoFeedScreen
import com.example.unplashapi.presentation.viewmodels.DetailViewModel
import com.example.unplashapi.presentation.viewmodels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnplashApiTheme {
                val navController = rememberNavController()
                val postViewModel: PostViewModel = hiltViewModel()
                val detailViewModel: DetailViewModel = hiltViewModel()
                AppNavGraph(
                    navHostController = navController,
                    postViewModel = postViewModel,
                    detailViewModel = detailViewModel
                )
            }
        }
    }
}