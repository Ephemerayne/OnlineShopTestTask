package com.nyx.favourites_impl.models

sealed class FavouritesViewAction {
    object Back: FavouritesViewAction()
    object OpenProductCard: FavouritesViewAction() // class
}
