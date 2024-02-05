package com.nyx.favourites_impl.models

sealed class FavouritesViewEvent {
    object OnBackClicked: FavouritesViewEvent()
    object ActionInvoked: FavouritesViewEvent()
}
