package com.nyx.catalog_impl.models

import com.nyx.common_api.common.ProgressState
import com.nyx.common_compose.models.ProductUiEntity

data class CatalogViewState(
    val products: List<ProductUiEntity> = listOf(),
    val isSortingMenuExpanded: Boolean = false,
    val filterData: FilterData = FilterData(),
    val filteredProducts: List<ProductUiEntity> = listOf(),
    val loadingProductsState: ProgressState<Any> = ProgressState.Progress
)

enum class SortingType {
    BY_POPULAR,
    BY_DECREASE_PRICE,
    BY_INCREASE_PRICE
}

enum class ProductTagType {
    ALL,
    FACE,
    BODY,
    SUNTAN,
    MASKS,
}


val ProductTagType.serverTag: String
    get() = when (this) {
        ProductTagType.ALL -> ""
        ProductTagType.FACE -> "face"
        ProductTagType.BODY -> "body"
        ProductTagType.SUNTAN -> "suntan"
        ProductTagType.MASKS -> "mask"
    }

data class FilterData(
    val currentSortingType: SortingType = SortingType.BY_POPULAR,
    val currentTag: ProductTagType = ProductTagType.ALL,
    val availableTags: List<ProductTagType> = listOf(
        ProductTagType.ALL,
        ProductTagType.FACE,
        ProductTagType.BODY,
        ProductTagType.SUNTAN,
        ProductTagType.MASKS
    ),
)