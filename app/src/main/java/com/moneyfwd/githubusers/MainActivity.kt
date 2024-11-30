package com.moneyfwd.githubusers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.features.profile.ui.ProfileScreen
import com.moneyfwd.features.search.ui.SearchScreen
import com.moneyfwd.githubusers.ui.theme.GithubUsersTheme
import com.moneyfwd.shared.navigation.NavDestination.ProfileScreen
import com.moneyfwd.shared.navigation.NavDestination.SearchScreen
import com.moneyfwd.shared.navigation.NavDestination.ExternalWebLink
import com.moneyfwd.shared.navigation.Navigator
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Navigation
            val navHostController = rememberNavController()
            get<Navigator>().setNavController(navHostController)

            //Compose UI
            GithubUsersTheme { MainComposition(navHostController) }
        }
    }

    @Composable
    fun MainComposition(navHostController: NavHostController) {
        val context = LocalContext.current

        NavHost(navController = navHostController, startDestination = SearchScreen.route) {
            composable(SearchScreen.route) { SearchScreen(viewModel = get()) }
            composable(ProfileScreen.route) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username") ?: ""
                ProfileScreen(username = Username(username), viewModel = remember { get() })
            }
            composable(ExternalWebLink.route) { backStackEntry ->
                val webLink = backStackEntry.arguments?.getString("webLink") ?: ""
                if (webLink.isNotEmpty()) {
                    // Open web link using Intent
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(webLink)
                    }
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}