package com.nyx.catalog_impl.models

sealed class CatalogViewAction {
    class OpenProductCard(val productId: String): CatalogViewAction()
}