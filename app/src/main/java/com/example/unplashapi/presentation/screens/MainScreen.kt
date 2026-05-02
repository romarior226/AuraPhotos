package com.example.unplashapi.presentation.screens

import UnplashApiTheme
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unplashapi.navgition.AppNavGraph
import com.example.unplashapi.navgition.NavItems
import com.example.unplashapi.navgition.Screen
import com.example.unplashapi.presentation.viewmodels.AuthViewModel
import com.example.unplashapi.presentation.viewmodels.DetailViewModel
import com.example.unplashapi.presentation.viewmodels.PostViewModel

@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    postViewModel: PostViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val myUsername by authViewModel.user.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val userIdInRoute = navBackStackEntry?.arguments?.getString("userId")
    Scaffold(
        bottomBar = {
            val showBottomBar = when (currentRoute) {
                Screen.PostsScreen.route -> true
                Screen.UserProfileScreen.route -> {
                    userIdInRoute != null && userIdInRoute == myUsername
                }

                else -> false
            }
            if (showBottomBar) {
                BottomBar(
                    currentRoute = currentRoute ?: "",
                    onItemClickListener = { navRoute ->
                        val finalRoute = if (navRoute == "profile_placeholder") {
                            Screen.UserProfileScreen.getRouteWithArgs(myUsername ?: "")
                        } else {
                            navRoute
                        }
                        if (navRoute != "profile_placeholder" || myUsername != null) {
                            navController.navigate(finalRoute) {
                                popUpTo(Screen.PostsScreen.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { _ ->
        Box() {
            AppNavGraph(
                navHostController = navController,
                postViewModel = postViewModel,
                detailViewModel = detailViewModel,
                authViewModel = authViewModel
            )
        }
    }
}

@Composable
fun BottomBar(
    currentRoute: String,
    onItemClickListener: (String) -> Unit
) {
    val items = listOf(
        NavItems.Home,
        NavItems.Profile
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val y = 0f
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }


    ) {
        items.forEach { item ->
            val selected = when {
                item.route == "profile_placeholder" -> currentRoute.startsWith("user_profile")
                else -> currentRoute == item.route
            }
            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemClickListener(item.route)
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                },
                colors = NavigationBarItemDefaults.colors(
                    // Робимо овал прозорим, щоб його не було видно
                    indicatorColor = Color.Transparent,
                    // Колір іконки, коли вона вибрана
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    // Колір іконки, коли вона НЕ вибрана
                    unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )

        }
    }
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBottomBar() {
    UnplashApiTheme {
        BottomBar(
            currentRoute = ""
        ) { }
    }

}
