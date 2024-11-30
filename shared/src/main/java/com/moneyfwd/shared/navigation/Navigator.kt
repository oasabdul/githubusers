package com.moneyfwd.shared.navigation

import androidx.navigation.NavHostController

interface Navigator {
    fun setNavController(navHostController: NavHostController)
    fun navigateTo(route: String)
    fun goBack()
}