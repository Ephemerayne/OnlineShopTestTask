package com.nyx.favourites_compose.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common_api.constant.Constants
import com.nyx.common_compose.models.ProductUiEntity
import com.nyx.common_compose.utils.toStable
import com.nyx.common_compose.viewmodel.rememberEvent
import com.nyx.common_compose.viewmodel.viewModelFactory
import com.nyx.common_compose.views.*
import com.nyx.favourites_api.navigation.FavouritesScreenNavigation
import com.nyx.favourites_compose.R
import com.nyx.favourites_compose.extensions.text
import com.nyx.favourites_compose.navigation.favouritesActionNavigation
import com.nyx.favourites_impl.models.FavouritesViewEvent
import com.nyx.favourites_impl.models.FavouritesViewModel
import com.nyx.favourites_impl.models.TabType

@Composable
fun FavouriteProductsScreen(
    screenNavigation: FavouritesScreenNavigation,
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    val viewModel: FavouritesViewModel = viewModel(
        factory = viewModelFactory {
            FavouritesViewModel(sharedPreferences = sharedPref)
        })

    val viewState = viewModel.viewStates().observeAsState().value
    val onBackClick = viewModel.rememberEvent(FavouritesViewEvent.OnBackClicked)
    val onTabClick = viewModel.rememberEvent<Int, _> { tabIndex ->
        FavouritesViewEvent.OnTabClicked(tabIndex)
    }
    val onFavouriteClick = viewModel.rememberEvent<String, Boolean, _> { id, isFavourite ->
        FavouritesViewEvent.OnFavouriteClicked(id, isFavourite)
    }
    val onProductClick = viewModel.rememberEvent<String, _> {
        FavouritesViewEvent.OnProductClicked(it)
    }

    FavouritesView(
        favouritesProducts = viewState.products,
        selectedTab = viewState.currentSelectedTab,
        onBackClick = onBackClick,
        onTabClick = onTabClick,
        onProductClick = onProductClick,
        onFavouriteClick = onFavouriteClick
    )

    favouritesActionNavigation(viewModel, screenNavigation)
}

@Composable
private fun FavouritesView(
    favouritesProducts: List<ProductUiEntity>,
    selectedTab: TabType,
    onBackClick: () -> Unit,
    onTabClick: (Int) -> Unit,
    onProductClick: (productId: String) -> Unit,
    onFavouriteClick: (String, Boolean) -> Unit,
) {
    Column {
        HeaderView(
            title = stringResource(R.string.favourites_title),
            onBackArrowClick = onBackClick,
        )
        VerticalSpacer(height = 4.dp)
        SwitchTabsView(
            selectedTabIndex = selectedTab.ordinal,
            tabsTitles = TabType.values().map { it.text }.toStable(),
            onTabClick = onTabClick
        )
        VerticalSpacer(height = 20.dp)

        Box {
            when (selectedTab) {
                TabType.PRODUCT -> {
                    if (favouritesProducts.isEmpty()) {
                        StubView(pageName = "favourite products")
                    } else {
                        ProductsGridView(
                            products = favouritesProducts,
                            onProductClick = onProductClick,
                            onFavouriteClick = onFavouriteClick
                        )
                    }
                }

                TabType.BRANDS -> {
                    StubView(pageName = "brands")
                }
            }
        }
    }
}
