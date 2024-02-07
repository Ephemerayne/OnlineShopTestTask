package com.nyx.onlineshoptesttask.navigation.screens.favourites

import androidx.navigation.NavController
import com.nyx.favourites_api.navigation.FavouritesScreenNavigation
import com.nyx.onlineshoptesttask.navigation.NavigationTree

class FavouritesScreenNavigationImpl(
    private val navController: NavController,
) : FavouritesScreenNavigation {

    override fun openProductCard(productId: String) {
        navController.navigate(
            route = NavigationTree.Root.Dashboard.Profile.Favourite.createRoute(
                route = NavigationTree.Root.Dashboard.Profile.ProductCard.name,
                args = productId
            )
        )
    }

    override fun back() {
        navController.popBackStack()
    }
}