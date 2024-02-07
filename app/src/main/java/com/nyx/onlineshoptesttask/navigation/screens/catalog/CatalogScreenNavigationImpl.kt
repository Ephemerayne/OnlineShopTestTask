package com.nyx.onlineshoptesttask.navigation.screens.catalog

import androidx.navigation.NavController
import com.nyx.catalog_api.navigation.CatalogScreenNavigation
import com.nyx.onlineshoptesttask.navigation.NavigationTree

class CatalogScreenNavigationImpl(
    private val navController: NavController,
) : CatalogScreenNavigation {

    override fun openProductCard(productId: String) {
        navController.navigate(
            route = NavigationTree.Root.Dashboard.Catalog.ProductsCatalog.createRoute(
                route = NavigationTree.Root.Dashboard.Catalog.ProductCard.name,
                args = productId
            )
        )
    }
}