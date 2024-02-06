package com.nyx.favourites_compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common_compose.utils.toStable
import com.nyx.common_compose.viewmodel.rememberEvent
import com.nyx.dashboard_compose.views.StubView
import com.nyx.favourites_api.navigation.FavouritesScreenNavigation
import com.nyx.favourites_compose.navigation.favouritesActionNavigation
import com.nyx.favourites_impl.models.FavouritesViewEvent
import com.nyx.favourites_impl.models.FavouritesViewModel
import com.nyx.favourites_impl.models.TabType

@Composable
fun FavouriteProductsScreen(
    viewModel: FavouritesViewModel = viewModel(),
    screenNavigation: FavouritesScreenNavigation,
) {
    val viewState = viewModel.viewStates().observeAsState().value
    val onBackClick = viewModel.rememberEvent(FavouritesViewEvent.OnBackClicked)
    val onTabClick = viewModel.rememberEvent<Int, _> { tabIndex ->
        FavouritesViewEvent.OnTabClicked(tabIndex)
    }
    val onProductClick = viewModel.rememberEvent(FavouritesViewEvent.OnProductClicked)

    FavouritesView(
        selectedTab = viewState.currentSelectedTab,
        onBackClick = onBackClick,
        onTabClick = onTabClick,
        onProductClick = onProductClick
    )

    favouritesActionNavigation(viewModel, screenNavigation)
}

@Composable
private fun FavouritesView(
    selectedTab: TabType,
    onBackClick: () -> Unit,
    onTabClick: (Int) -> Unit,
    onProductClick: () -> Unit
) {
    Column {
        com.nyx.common_compose.views.HeaderView("Избранное", onBackArrowClick = onBackClick)
        com.nyx.common_compose.views.VerticalSpacer(height = 4.dp)
        com.nyx.common_compose.views.SwitchTabsView(
            selectedTabIndex = selectedTab.ordinal,
            tabsTitles = TabType.values().map { it.title }.toStable(),
            onTabClick = onTabClick
        )
        com.nyx.common_compose.views.VerticalSpacer(height = 20.dp)

        Box {
            when (selectedTab) {
                TabType.PRODUCT -> {
                    com.nyx.common_compose.views.ProductsGridView(onProductClick = onProductClick)
                }

                TabType.BRANDS -> {
                    StubView(pageName = "brands")
                }
            }
        }
    }
}
