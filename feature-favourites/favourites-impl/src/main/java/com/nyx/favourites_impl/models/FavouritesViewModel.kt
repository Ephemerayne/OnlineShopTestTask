package com.nyx.favourites_impl.models

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.nyx.common_compose.mappers.toUiEntity
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.common_data.local.FavouriteProductStorage
import com.nyx.common_data.repository.ProductRepositoryImpl
import kotlinx.coroutines.launch

class FavouritesViewModel(
    sharedPreferences: SharedPreferences,
) :
    BaseViewModel<FavouritesViewState, FavouritesViewAction, FavouritesViewEvent>(
        initialState = FavouritesViewState()
    ) {

    private val repository =
        ProductRepositoryImpl(favouriteProductStorage = FavouriteProductStorage(sharedPreferences))

    init {
        fetchProducts()
    }

    override fun obtainEvent(viewEvent: FavouritesViewEvent) {
        when (viewEvent) {
            is FavouritesViewEvent.OnBackClicked -> viewAction = FavouritesViewAction.Back
            is FavouritesViewEvent.OnTabClicked -> setTab(viewEvent.tabIndex)
            is FavouritesViewEvent.OnProductClicked -> viewAction =
                FavouritesViewAction.OpenProductCard(viewEvent.productId)

            is FavouritesViewEvent.OnFavouriteClicked -> toggleProductToFavourites(
                viewEvent.id,
                viewEvent.isFavourite
            )

            is FavouritesViewEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun setTab(selectedTabIndex: Int) {
        viewState = viewState.copy(currentSelectedTab = TabType.values()[selectedTabIndex])
    }

    private fun toggleProductToFavourites(productId: String, isFavourite: Boolean) {
        if (isFavourite) {
            repository.deleteFavourite(productId)
        } else {
            repository.addFavourite(productId)
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            repository.getFavourite().collect {
                viewState = viewState.copy(products = it.map { it.toUiEntity() })
            }
        }
    }
}