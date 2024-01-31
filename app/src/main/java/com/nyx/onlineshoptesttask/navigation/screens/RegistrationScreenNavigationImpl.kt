package com.nyx.onlineshoptesttask.navigation.screens

import androidx.navigation.NavController
import com.nyx.onlineshoptesttask.navigation.NavigationTree
import com.nyx.registration_api.navigation.RegistrationScreenNavigation

class RegistrationScreenNavigationImpl(
    private val navController: NavController,
) : RegistrationScreenNavigation {

    override fun goToCatalog() {
        navController.navigate(route = NavigationTree.Root.Dashboard.Catalog.ProductsCatalog.name)
    }
}