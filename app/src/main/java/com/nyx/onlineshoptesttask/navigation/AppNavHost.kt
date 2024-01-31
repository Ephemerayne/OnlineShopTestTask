package com.nyx.onlineshoptesttask.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nyx.onlineshoptesttask.navigation.screens.RegistrationScreenNavigationImpl
import com.nyx.registration_compose.screens.RegistrationScreen

private const val ARGS = "args"

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationTree.Root.Dashboard.Main.Home.name,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        val registrationScreenNavigation = RegistrationScreenNavigationImpl(navController)

        composable(startDestination) {
            RegistrationScreen(
                screenNavigation = registrationScreenNavigation
            )
        }
    }
}
