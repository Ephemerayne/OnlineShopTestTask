package com.nyx.catalog_impl.models

data class CatalogViewState(
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