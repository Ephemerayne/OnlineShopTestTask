package com.nyx.onlineshoptesttask.navigation.screens.registration

import androidx.navigation.NavController
import com.nyx.registration_api.navigation.RegistrationScreenNavigation

class RegistrationScreenNavigationImpl(
    private val navController: NavController,
    private val onNavigateToDashboard: () -> Unit
) : RegistrationScreenNavigation {

    override fun openCatalog() {
        onNavigateToDashboard()
    }
}