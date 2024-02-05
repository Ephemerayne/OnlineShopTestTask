package com.nyx.favourites_impl.models

import com.nyx.common.viewmodel.BaseViewModel

class FavouritesViewModel :
    BaseViewModel<FavouritesViewState, FavouritesViewAction, FavouritesViewEvent>(
        initialState = FavouritesViewState()
    ) {

    override fun obtainEvent(viewEvent: FavouritesViewEvent) {
        when (viewEvent) {
            is FavouritesViewEvent.OnBackClicked -> viewAction = FavouritesViewAction.Back
            is FavouritesViewEvent.ActionInvoked -> viewAction = null
        }
    }
}