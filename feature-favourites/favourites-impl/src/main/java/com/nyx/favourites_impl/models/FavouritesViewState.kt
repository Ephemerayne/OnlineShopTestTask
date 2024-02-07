package com.nyx.favourites_impl.models

import com.nyx.common_compose.models.ProductUiEntity

data class FavouritesViewState(
    val products: List<ProductUiEntity> = listOf(),
    val currentSelectedTab: TabType = TabType.PRODUCT,
)

enum class TabType {
    PRODUCT,
    BRANDS
}