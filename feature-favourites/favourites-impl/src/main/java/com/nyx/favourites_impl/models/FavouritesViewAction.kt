package com.nyx.favourites_impl.models

sealed class FavouritesViewAction {
    object Back: FavouritesViewAction()
    class OpenProductCard(val productId: String): FavouritesViewAction()
}
