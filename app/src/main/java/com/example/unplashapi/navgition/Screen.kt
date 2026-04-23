package com.example.unplashapi.navgition

sealed class Screen(
    val route: String
) {
    object PostsScreen : Screen(POSTS_SCREEN)

    object FavouriteScreen : Screen(FAVOURITE_SCREEN)
    object DetailedPostScreen : Screen(DETAILED_SCREEN) {
        const val ROUTE_FOR_ARGS = "detailed_screen"
        fun getRouteWithArgs(postId: String): String {
            return "$ROUTE_FOR_ARGS/$postId"

        }
    }

    companion object {
        const val FAVOURITE_SCREEN = "favourite_screen"
        const val POSTS_SCREEN = "posts_screen"
        const val DETAILED_SCREEN = "detailed_screen/{postId}"

    }
}