package com.nyx.favourites_impl.models

sealed class FavouritesViewEvent {
    object OnBackClicked: FavouritesViewEvent()
    class OnTabClicked(val tabIndex: Int): FavouritesViewEvent()
    object OnProductClicked: FavouritesViewEvent()
    object ActionInvoked: FavouritesViewEvent()
}
