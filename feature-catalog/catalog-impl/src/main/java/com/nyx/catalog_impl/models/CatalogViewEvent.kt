package com.nyx.catalog_impl.models

sealed class CatalogViewEvent {
    object OnSortingDropDownMenuClicked : CatalogViewEvent()
    class OnSortingVariantClicked(val type: SortingType) : CatalogViewEvent()
    class OnTagClicked(val type: ProductTagType) : CatalogViewEvent()
    object OnClearTagClicked : CatalogViewEvent()
    class OnProductClicked(val productId: String) : CatalogViewEvent()
    object ActionInvoked : CatalogViewEvent()
    class OnFavouriteClicked(val productId: String, val isFavourite: Boolean) : CatalogViewEvent()
}