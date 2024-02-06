package com.nyx.catalog_impl.models

sealed class CatalogViewAction {
    object OpenProductCard: CatalogViewAction() // class with pass product
}