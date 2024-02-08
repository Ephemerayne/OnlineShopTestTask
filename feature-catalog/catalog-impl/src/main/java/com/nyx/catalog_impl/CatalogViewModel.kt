package com.nyx.catalog_impl

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.nyx.catalog_impl.models.CatalogViewAction
import com.nyx.catalog_impl.models.CatalogViewEvent
import com.nyx.catalog_impl.models.CatalogViewState
import com.nyx.catalog_impl.models.ProductTagType
import com.nyx.catalog_impl.models.SortingType
import com.nyx.catalog_impl.models.serverTag
import com.nyx.common_compose.mappers.toUiEntity
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.common_data.local.product.FavouriteProductStorage
import com.nyx.common_data.repository.product.ProductRepositoryImpl
import kotlinx.coroutines.launch

// TODO: REFACTOR FILTERING: CREATE FILTER DATA CLASS

class CatalogViewModel
    (sharedPreferences: SharedPreferences) :
    BaseViewModel<CatalogViewState, CatalogViewAction, CatalogViewEvent>(
        initialState = CatalogViewState()
    ) {

    private val repository = ProductRepositoryImpl(
        FavouriteProductStorage(sharedPreferences)
    )

    init {
        fetchProducts()
    }

    override fun obtainEvent(viewEvent: CatalogViewEvent) {
        when (viewEvent) {
            is CatalogViewEvent.OnSortingDropDownMenuClicked -> toggleSortingDropDownMenu()
            is CatalogViewEvent.OnSortingVariantClicked -> setCurrentSortingType(viewEvent.type)
            is CatalogViewEvent.OnTagClicked -> setProductTag(viewEvent.type)
            is CatalogViewEvent.OnClearTagClicked -> resetTags()
            is CatalogViewEvent.OnProductClicked -> openProductCard(viewEvent.productId)
            is CatalogViewEvent.OnFavouriteClicked -> toggleProductToFavourites(
                viewEvent.productId,
                viewEvent.isFavourite
            )

            is CatalogViewEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            repository.getProducts().collect {
                val products = it
                    .map { entity -> entity.toUiEntity() }
                    .sortedByDescending { it.feedback.rating }

                viewState = viewState.copy(
                    allProducts = products,
                    filteredProducts = products,
                )
            }
        }
    }

    private fun toggleSortingDropDownMenu() {
        viewState = viewState.copy(isSortingMenuExpanded = !viewState.isSortingMenuExpanded)
    }

    private fun setCurrentSortingType(type: SortingType) {
        val sortedProducts = when (type) {
            SortingType.BY_POPULAR -> viewState.allProducts.sortedByDescending { it.feedback.rating }
            SortingType.BY_DECREASE_PRICE -> viewState.allProducts.sortedByDescending { it.price.priceWithDiscount.toInt() }
            SortingType.BY_INCREASE_PRICE -> viewState.allProducts.sortedBy { it.price.priceWithDiscount.toInt() }
        }

        viewState = viewState.copy(
            currentSortingType = type,
            isSortingMenuExpanded = !viewState.isSortingMenuExpanded,
            allProducts = sortedProducts,
            filteredProducts = sortedProducts
        )
    }

    private fun setProductTag(tagType: ProductTagType) {
        val serverTag = tagType.serverTag

        val reorderedTags = mutableListOf<ProductTagType>().apply {
            addAll(viewState.availableTags)
        }

        reorderedTags.removeIf { it == tagType }
        reorderedTags.add(0, tagType)

        viewState = viewState.copy(
            currentTag = tagType,
            availableTags = reorderedTags,
            filteredProducts = viewState.allProducts.filter { it.tags.contains(serverTag) }
        )
    }

    private fun resetTags() {
        viewState = CatalogViewState(
            currentSortingType = viewState.currentSortingType,
            allProducts = viewState.allProducts,
            filteredProducts = viewState.allProducts,
        )
    }

    private fun openProductCard(productId: String) {
        viewAction = CatalogViewAction.OpenProductCard(productId)
    }

    private fun toggleProductToFavourites(productId: String, isFavourite: Boolean) {
        if (isFavourite) {
            repository.deleteFavourite(productId)
        } else {
            repository.addFavourite(productId)
        }
    }
}