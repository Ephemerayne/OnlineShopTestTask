package com.nyx.onlineshoptesttask.navigation.screens.registration

import androidx.navigation.NavController
import com.nyx.onlineshoptesttask.navigation.NavigationTree
import com.nyx.registration_api.navigation.LaunchScreenNavigation

class LaunchScreenNavigationImpl(
    private val navController: NavController,
    private val onNavigateToDashboard: () -> Unit
): LaunchScreenNavigation {

    override fun openCatalog() {
        onNavigateToDashboard()
    }

    override fun openRegistration() {
        navController.navigate(NavigationTree.Root.Registration.name)
    }
}