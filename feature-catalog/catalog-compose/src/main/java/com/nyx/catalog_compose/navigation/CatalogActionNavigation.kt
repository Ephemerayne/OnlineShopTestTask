package com.nyx.catalog_compose.navigation

import androidx.compose.runtime.Composable
import com.nyx.catalog_api.navigation.CatalogScreenNavigation
import com.nyx.catalog_impl.CatalogViewModel
import com.nyx.catalog_impl.models.CatalogViewAction
import com.nyx.catalog_impl.models.CatalogViewEvent
import com.nyx.common_compose.viewmodel.observeAction

@Composable
fun catalogActionNavigation(
    viewModel: CatalogViewModel,
    navigation: CatalogScreenNavigation,
) {
    viewModel.observeAction(CatalogViewEvent.ActionInvoked) { action ->
        when (action) {
            is CatalogViewAction.OpenProductCard -> {
                navigation.openProductCard(action.productId)
            }
        }
    }
}