package com.nyx.catalog_impl.models

sealed class CatalogViewEvent {
    object OnSortingDropDownMenuClicked: CatalogViewEvent()
    class OnSortingVariantClicked(val type: SortingType): CatalogViewEvent()
    class OnTagClicked(val type: ProductTagType): CatalogViewEvent()
    object OnClearTagClicked: CatalogViewEvent()
    object OnProductClicked: CatalogViewEvent() // class with product
    object ActionInvoked: CatalogViewEvent() // class with product
}