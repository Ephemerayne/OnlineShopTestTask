package com.nyx.onlineshoptesttask.navigation.screens.favourites

import androidx.navigation.NavController
import com.nyx.favourites_api.navigation.FavouritesScreenNavigation
import com.nyx.onlineshoptesttask.navigation.NavigationTree

class FavouritesScreenNavigationImpl(
    private val navController: NavController,
) : FavouritesScreenNavigation {

    override fun openProductCard() {
        navController.navigate(NavigationTree.Root.Dashboard.Profile.ProductCard.name)
    }

    override fun back() {
        navController.popBackStack()
    }
}