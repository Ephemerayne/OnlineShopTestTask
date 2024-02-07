package com.nyx.onlineshoptesttask.navigation.screens.registration

import androidx.navigation.NavController
import com.nyx.onlineshoptesttask.navigation.NavigationTree
import com.nyx.registration_api.navigation.LaunchScreenNavigation

class LaunchScreenNavigationImpl(
    private val navController: NavController
): LaunchScreenNavigation {

    override fun openCatalog() {
        navController.navigate(NavigationTree.Root.Dashboard.Catalog.ProductsCatalog.name)
    }

    override fun openRegistration() {
        navController.navigate(NavigationTree.Root.Registration.name)
    }
}