package com.nyx.catalog_impl.models

import com.nyx.common_compose.models.ProductUiEntity

data class CatalogViewState(
    val allProducts: List<ProductUiEntity> = listOf(),
    val filteredProducts: List<ProductUiEntity> = listOf(),
    val isSortingMenuExpanded: Boolean = false,
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