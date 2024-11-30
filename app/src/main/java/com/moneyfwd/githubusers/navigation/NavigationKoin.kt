package com.moneyfwd.githubusers.navigation

import com.moneyfwd.shared.navigation.Navigator
import org.koin.dsl.module

object NavigationKoin {
    val module = module {
        single<Navigator> { NavigationController() }
    }
}