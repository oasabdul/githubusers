package com.moneyfwd.shared.navigation

sealed class NavDestination(val route: String) {
    data object SearchScreen : NavDestination("search")
    data object ProfileScreen : NavDestination("profile/{username}") {
        fun createRoute(username: String) = "profile/$username"
    }

    data object ExternalWebLink : NavDestination("external/{webLink}") {
        fun createRoute(webLink: String) = "external/$webLink"
    }
}