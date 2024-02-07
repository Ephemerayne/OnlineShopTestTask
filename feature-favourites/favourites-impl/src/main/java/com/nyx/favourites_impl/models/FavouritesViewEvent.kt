package com.nyx.favourites_impl.models

sealed class FavouritesViewEvent {
    object OnBackClicked : FavouritesViewEvent()
    class OnTabClicked(val tabIndex: Int) : FavouritesViewEvent()
    class OnProductClicked(val productId: String) : FavouritesViewEvent()
    class OnFavouriteClicked(val id: String, val isFavourite: Boolean) : FavouritesViewEvent()
    object ActionInvoked : FavouritesViewEvent()
}
