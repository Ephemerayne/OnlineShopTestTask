package com.nyx.favourites_compose.navigation

import androidx.compose.runtime.Composable
import com.nyx.common_compose.viewmodel.observeAction
import com.nyx.favourites_api.navigation.FavouritesScreenNavigation
import com.nyx.favourites_impl.models.FavouritesViewAction
import com.nyx.favourites_impl.models.FavouritesViewEvent
import com.nyx.favourites_impl.models.FavouritesViewModel


@Composable
fun favouritesActionNavigation(
    viewModel: FavouritesViewModel,
    screenNavigation: FavouritesScreenNavigation,
) {
    viewModel.observeAction(FavouritesViewEvent.ActionInvoked) { action ->
        when (action) {
            is FavouritesViewAction.Back -> {
                screenNavigation.back()
            }
            is FavouritesViewAction.OpenProductCard -> {
                screenNavigation.openProductCard(action.productId)
            }
        }
    }
}