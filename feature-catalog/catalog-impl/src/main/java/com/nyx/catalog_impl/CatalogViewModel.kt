package com.nyx.catalog_impl

import androidx.lifecycle.viewModelScope
import com.nyx.catalog_impl.models.CatalogViewAction
import com.nyx.catalog_impl.models.CatalogViewEvent
import com.nyx.catalog_impl.models.CatalogViewState
import com.nyx.catalog_impl.models.FilterData
import com.nyx.catalog_impl.models.ProductTagType
import com.nyx.catalog_impl.models.SortingType
import com.nyx.catalog_impl.models.serverTag
import com.nyx.common_api.repository.product.ProductRepository
import com.nyx.common_compose.mappers.toUiEntity
import com.nyx.common_compose.models.ProductUiEntity
import com.nyx.common_compose.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: REFACTOR FILTERING: CREATE FILTER DATA CLASS

@HiltViewModel
class CatalogViewModel @Inject constructor(private val productRepository: ProductRepository) :
    BaseViewModel<CatalogViewState, CatalogViewAction, CatalogViewEvent>(
        initialState = CatalogViewState()
    ) {

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
            productRepository.getProducts().collect {
                val products = it
                    .map { entity -> entity.toUiEntity() }
                    .sortedByDescending { it.feedback.rating }

                viewState = viewState.copy(
                    products = products,
                    filteredProducts = products
                )
            }
        }
    }

    private fun toggleSortingDropDownMenu() {
        viewState = viewState.copy(isSortingMenuExpanded = !viewState.isSortingMenuExpanded)
    }

    private fun setCurrentSortingType(type: SortingType) {
        viewState = viewState.copy(
            filterData = viewState.filterData.copy(currentSortingType = type),
            isSortingMenuExpanded = !viewState.isSortingMenuExpanded,
        )

        applyFilter(viewState.filterData)
    }

    private fun applyFilter(filterData: FilterData) {
        val sortedProducts = when (filterData.currentSortingType) {
            SortingType.BY_POPULAR -> viewState.products.sortedByDescending { it.feedback.rating }
            SortingType.BY_DECREASE_PRICE -> viewState.products.sortedByDescending { it.price.priceWithDiscount.toInt() }
            SortingType.BY_INCREASE_PRICE -> viewState.products.sortedBy { it.price.priceWithDiscount.toInt() }
        }

        applyTagFilter(sortedProducts)
    }

    private fun applyTagFilter(sortedProducts: List<ProductUiEntity>) {
        val serverTag = viewState.filterData.currentTag.serverTag

        val sortedFilteredProducts = if (serverTag.isEmpty()) {
            sortedProducts
        } else {
            sortedProducts.filter { it.tags.contains(serverTag) }
        }

        val reorderedTags = mutableListOf<ProductTagType>().apply {
            addAll(viewState.filterData.availableTags)
        }

        reorderedTags.removeIf { it == viewState.filterData.currentTag }
        reorderedTags.add(0, viewState.filterData.currentTag)

        viewState = viewState.copy(
            filterData = viewState.filterData.copy(
                availableTags = reorderedTags,
            ),
            filteredProducts = sortedFilteredProducts
        )
    }

    private fun setProductTag(tagType: ProductTagType) {
        viewState = viewState.copy(
            filterData = viewState.filterData.copy(
                currentTag = tagType
            )
        )
        applyFilter(viewState.filterData)
    }

    private fun resetTags() {
        viewState = viewState.copy(
            filterData = FilterData(
                currentSortingType = viewState.filterData.currentSortingType
            ),
        )

        applyFilter(viewState.filterData)
    }

    private fun openProductCard(productId: String) {
        viewAction = CatalogViewAction.OpenProductCard(productId)
    }

    private fun toggleProductToFavourites(productId: String, isFavourite: Boolean) {
        if (isFavourite) {
            productRepository.deleteFavourite(productId)
        } else {
            productRepository.addFavourite(productId)
        }

        applyFilter(viewState.filterData)
    }
}