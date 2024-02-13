package com.nyx.favourites_impl.models

import androidx.lifecycle.viewModelScope
import com.nyx.common_api.repository.product.ProductRepository
import com.nyx.common_compose.mappers.toUiEntity
import com.nyx.common_compose.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) :
    BaseViewModel<FavouritesViewState, FavouritesViewAction, FavouritesViewEvent>(
        initialState = FavouritesViewState()
    ) {

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
            productRepository.deleteFavourite(productId)
        } else {
            productRepository.addFavourite(productId)
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            productRepository.getFavourite().collect {
                viewState = viewState.copy(products = it.map { it.toUiEntity() })
            }
        }
    }
}