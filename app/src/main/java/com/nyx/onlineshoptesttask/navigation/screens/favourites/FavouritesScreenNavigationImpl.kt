package com.nyx.onlineshoptesttask.navigation.screens.favourites

import androidx.navigation.NavController
import com.nyx.favourites_api.navigation.FavouritesScreenNavigation

class FavouritesScreenNavigationImpl(
    private val navController: NavController
) : FavouritesScreenNavigation {

    override fun back() {
        navController.popBackStack()
    }
}