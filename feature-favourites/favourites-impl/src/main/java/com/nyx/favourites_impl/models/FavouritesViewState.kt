package com.nyx.favourites_impl.models

data class FavouritesViewState(
    val currentSelectedTab: TabType = TabType.PRODUCT
)

enum class TabType(val title: String) {
    PRODUCT("Продукты"), BRANDS("Бренды")
}