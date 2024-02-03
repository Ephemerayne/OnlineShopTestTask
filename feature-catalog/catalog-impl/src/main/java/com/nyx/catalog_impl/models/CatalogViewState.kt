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

enum class SortingType(val text: String) {
    BY_POPULAR("По популярности"),
    BY_DECREASE_PRICE("По уменьшению цены"),
    BY_INCREASE_PRICE("По возрастанию цены")
}

enum class ProductTagType(val text: String) {
    ALL("Смотреть все"),
    FACE("Лицо"),
    BODY("Тело"),
    SUNTAN("Загар"),
    MASKS("Маски")
}