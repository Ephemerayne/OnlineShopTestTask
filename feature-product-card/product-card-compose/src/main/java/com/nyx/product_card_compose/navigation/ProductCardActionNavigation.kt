package com.nyx.product_card_compose.navigation

import androidx.compose.runtime.Composable
import com.nyx.common.viewmodel.observeAction
import com.nyx.product_card_api.navigation.ProductCardScreenNavigation
import com.nyx.product_card_impl.ProductCardViewModel
import com.nyx.product_card_impl.models.ProductCardViewAction
import com.nyx.product_card_impl.models.ProductCardViewEvent


@Composable
fun productCardActionNavigation(
    viewModel: ProductCardViewModel,
    screenNavigation: ProductCardScreenNavigation,
) {
    viewModel.observeAction(ProductCardViewEvent.ActionInvoked) { action ->
        when (action) {
            is ProductCardViewAction.Back -> {
                screenNavigation.back()
            }
        }
    }
}