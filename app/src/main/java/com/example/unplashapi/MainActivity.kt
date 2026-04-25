package com.example.unplashapi

import UnplashApiTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.unplashapi.navgition.AppNavGraph
import com.example.unplashapi.presentation.viewmodels.AuthViewModel
import com.example.unplashapi.presentation.viewmodels.DetailViewModel
import com.example.unplashapi.presentation.viewmodels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    private var pendingCode: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pendingCode = intent.data?.getQueryParameter("code")
        handleIntent(intent)
        setContent {
            UnplashApiTheme {
                val navController = rememberNavController()
                val postViewModel: PostViewModel = hiltViewModel()
                val detailViewModel: DetailViewModel = hiltViewModel()

                AppNavGraph(
                    navHostController = navController,
                    postViewModel = postViewModel,
                    detailViewModel = detailViewModel,
                    authViewModel = authViewModel
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val code = intent.data?.getQueryParameter("code")
        if (code != null) {
            authViewModel.loadToken(code)
        }
    }
}