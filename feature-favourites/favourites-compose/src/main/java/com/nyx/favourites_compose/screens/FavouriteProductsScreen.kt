package com.nyx.favourites_compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nyx.common.viewmodel.rememberEvent
import com.nyx.common.views.HeaderView
import com.nyx.common.views.ProductsGridView
import com.nyx.common.views.VerticalSpacer
import com.nyx.favourites_api.navigation.FavouritesScreenNavigation
import com.nyx.favourites_compose.navigation.favouritesActionNavigation
import com.nyx.favourites_impl.models.FavouritesViewEvent
import com.nyx.favourites_impl.models.FavouritesViewModel

@Composable
fun FavouriteProductsScreen(
    viewModel: FavouritesViewModel = viewModel(),
    screenNavigation: FavouritesScreenNavigation,
) {
    //val viewState = viewModel.viewStates().observeAsState().value
    val onBackClick = viewModel.rememberEvent(FavouritesViewEvent.OnBackClicked)

    FavouritesView(onBackClick = onBackClick)

    favouritesActionNavigation(viewModel, screenNavigation)
}

@Composable
private fun FavouritesView(onBackClick: () -> Unit) {
    Column {
        HeaderView("Избранное", onBackArrowClick = onBackClick)
        VerticalSpacer(height = 32.dp)
        ProductsGridView {

        }
    }
}