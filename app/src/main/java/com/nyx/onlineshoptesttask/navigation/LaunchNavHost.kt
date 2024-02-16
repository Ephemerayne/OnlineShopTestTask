package com.nyx.onlineshoptesttask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nyx.onlineshoptesttask.navigation.screens.registration.LaunchScreenNavigationImpl
import com.nyx.onlineshoptesttask.navigation.screens.registration.RegistrationScreenNavigationImpl
import com.nyx.registration_compose.screens.LauncherScreen
import com.nyx.registration_compose.screens.RegistrationScreen

@Composable
fun LaunchNavHost(onNavigateToDashboard: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationTree.Root.Launch.name,
    ) {
        val registrationScreenNavigation =
            RegistrationScreenNavigationImpl(navController, onNavigateToDashboard)
        val launchScreenNavigation =
            LaunchScreenNavigationImpl(navController, onNavigateToDashboard)

        composable(NavigationTree.Root.Launch.name) {
            LauncherScreen(
                screenNavigation = launchScreenNavigation
            )
        }

        composable(NavigationTree.Root.Registration.name) {
            RegistrationScreen(
                navController,
                screenNavigation = registrationScreenNavigation
            )
        }
    }
}