package com.nyx.onlineshoptesttask.navigation.screens.product_card

import androidx.navigation.NavController
import com.nyx.product_card_api.navigation.ProductCardScreenNavigation

class ProductCardScreenNavigationImpl(
    private val navController: NavController,
) : ProductCardScreenNavigation {

    override fun back() {
        navController.popBackStack()
    }
}