package com.moneyfwd.githubusers.navigation

import androidx.navigation.NavHostController
import com.moneyfwd.shared.navigation.Navigator

class NavigationController: Navigator {
    private var navHostController: NavHostController? = null

    override fun setNavController(navHostController: NavHostController) {
        this.navHostController = navHostController
    }

    override fun navigateTo(route: String) {
        navHostController?.navigate(route)
    }

    override fun goBack() {
        navHostController?.popBackStack()
    }
}