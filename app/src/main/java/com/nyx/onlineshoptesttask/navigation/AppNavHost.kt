package com.nyx.onlineshoptesttask.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nyx.onlineshoptesttask.navigation.screens.registration.LaunchScreenNavigationImpl
import com.nyx.onlineshoptesttask.navigation.screens.registration.RegistrationScreenNavigationImpl
import com.nyx.onlineshoptesttask.ui.views.DashboardNavigationBar
import com.nyx.registration_compose.screens.LauncherScreen
import com.nyx.registration_compose.screens.RegistrationScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationTree.Root.Launch.name,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        val registrationScreenNavigation = RegistrationScreenNavigationImpl(navController)
        val launchScreenNavigation = LaunchScreenNavigationImpl(navController)

        composable(startDestination) {
            LauncherScreen(screenNavigation = launchScreenNavigation)
        }

        composable(NavigationTree.Root.Registration.name) {
            RegistrationScreen(
                screenNavigation = registrationScreenNavigation
            )
        }

        composable(route = NavigationTree.Root.Dashboard.Catalog.ProductsCatalog.name) {
            DashboardNavigationBar()
        }
    }
}
