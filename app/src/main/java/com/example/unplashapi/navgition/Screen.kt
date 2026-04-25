package com.example.unplashapi.navgition

sealed class Screen(
    val route: String
) {
    object PostsScreen : Screen(POSTS_SCREEN)

    object AuthScreen : Screen(AUTH_SCREEN)

    object FavouriteScreen : Screen(FAVOURITE_SCREEN)
    object DetailedPostScreen : Screen(DETAILED_SCREEN) {
        const val ROUTE_FOR_ARGS = "detailed_screen"
        fun getRouteWithArgs(postId: String): String {
            return "$ROUTE_FOR_ARGS/$postId"

        }
    }

    object UserProfileScreen : Screen(USER_SCREEN) {
        const val ROUTE_FOR_ARGS = "user_screen"
        fun getRouteWithArgs(userName: String): String {
            return "$ROUTE_FOR_ARGS/$userName"

        }
    }


    companion object {
        const val FAVOURITE_SCREEN = "favourite_screen"
        const val POSTS_SCREEN = "posts_screen"
        const val AUTH_SCREEN = "auth_screen"
        const val DETAILED_SCREEN = "detailed_screen/{postId}"

        const val USER_SCREEN = "user_screen/{userId}"

    }
}