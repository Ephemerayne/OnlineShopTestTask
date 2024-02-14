package com.nyx.onlineshoptesttask.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

enum class NavType {
    DASHBOARD,
    REGISTRATION
}

@Composable
fun AppNavHost() {
    val navType = remember { mutableStateOf(NavType.REGISTRATION) }

    when (navType.value) {
        NavType.DASHBOARD -> DashboardNavigationBar(onNavigateToRegistration = {
            navType.value = NavType.REGISTRATION
        })

        NavType.REGISTRATION -> LaunchNavHost(onNavigateToDashboard = {
            navType.value = NavType.DASHBOARD
        })
    }
}
