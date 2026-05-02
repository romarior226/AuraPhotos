package com.example.unplashapi

import UnplashApiTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.unplashapi.presentation.screens.MainScreen
import com.example.unplashapi.presentation.viewmodels.AuthViewModel
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
                MainScreen(authViewModel = authViewModel)
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


