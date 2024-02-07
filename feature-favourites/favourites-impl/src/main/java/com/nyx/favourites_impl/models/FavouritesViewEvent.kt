package com.nyx.favourites_impl.models

sealed class FavouritesViewEvent {
    object OnBackClicked: FavouritesViewEvent()
    class OnTabClicked(val tabIndex: Int): FavouritesViewEvent()
    object OnProductClicked: FavouritesViewEvent()
    class OnFavouriteClicked(val id: String, val isFavourite: Boolean): FavouritesViewEvent()
    object ActionInvoked: FavouritesViewEvent()
}
