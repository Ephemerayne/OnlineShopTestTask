package com.nyx.onlineshoptesttask.navigation.screens.profile

import androidx.navigation.NavController
import com.nyx.onlineshoptesttask.navigation.NavigationTree
import com.nyx.profile_api.navigation.ProfileScreenNavigation

class ProfileScreenNavigationImpl(
    private val navController: NavController
): ProfileScreenNavigation {

    override fun goToFavourites() {
        navController.navigate(NavigationTree.Root.Dashboard.Profile.Favourite.name)
    }
}