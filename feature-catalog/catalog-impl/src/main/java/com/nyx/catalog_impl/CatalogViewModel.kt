package com.nyx.catalog_impl

import androidx.lifecycle.viewModelScope
import com.nyx.catalog_impl.models.CatalogViewAction
import com.nyx.catalog_impl.models.CatalogViewEvent
import com.nyx.catalog_impl.models.CatalogViewState
import com.nyx.catalog_impl.models.ProductTagType
import com.nyx.catalog_impl.models.SortingType
import com.nyx.common_compose.mappers.toUiEntity
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.common_data.repository.ProductRepositoryImpl
import kotlinx.coroutines.launch

class CatalogViewModel : BaseViewModel<CatalogViewState, CatalogViewAction, CatalogViewEvent>(
    initialState = CatalogViewState()
) {

    private val repository = ProductRepositoryImpl()

    init {
        fetchProducts()
    }

    override fun obtainEvent(viewEvent: CatalogViewEvent) {
        when (viewEvent) {
            is CatalogViewEvent.OnSortingDropDownMenuClicked -> toggleSortingDropDownMenu()
            is CatalogViewEvent.OnSortingVariantClicked -> setCurrentSortingType(viewEvent.type)
            is CatalogViewEvent.OnTagClicked -> setProductTag(viewEvent.type)
            is CatalogViewEvent.OnClearTagClicked -> resetTags()
            is CatalogViewEvent.OnProductClicked -> openProductCard()
            is CatalogViewEvent.OnFavouriteClicked -> addProductToFavourites()
            is CatalogViewEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            repository.getProducts().collect {
                viewState = viewState.copy(products = it.map { entity -> entity.toUiEntity() })
            }
        }
    }

    private fun toggleSortingDropDownMenu() {
        viewState = viewState.copy(isSortingMenuExpanded = !viewState.isSortingMenuExpanded)
    }

    private fun setCurrentSortingType(type: SortingType) {
        viewState = viewState.copy(
            currentSortingType = type,
            isSortingMenuExpanded = !viewState.isSortingMenuExpanded
        )
    }

    private fun setProductTag(tagType: ProductTagType) {
        val reorderedTags = mutableListOf<ProductTagType>().apply {
            addAll(viewState.availableTags)
        }

        reorderedTags.removeIf { it == tagType }
        reorderedTags.add(0, tagType)

        viewState = viewState.copy(
            currentTag = tagType,
            availableTags = reorderedTags
        )
    }

    private fun resetTags() {
        viewState = CatalogViewState(currentSortingType = viewState.currentSortingType)
    }

    private fun openProductCard() {
        viewAction = CatalogViewAction.OpenProductCard
    }

    private fun addProductToFavourites() {
        // database note
    }
}