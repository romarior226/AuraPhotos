package com.example.unplashapi.navgition

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItems(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : NavItems(
        route = Screen.PostsScreen.route,
        title = "Home",
        icon = Icons.Default.Home
    )

    object Search : NavItems(
        route = "",
        title = "Улюблені",
        icon = Icons.Default.Search
    )

    object Profile : NavItems(
        route = "profile_placeholder",
        title = "Person",
        icon = Icons.Default.Person
    )
}