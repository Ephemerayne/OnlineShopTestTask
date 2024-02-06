package com.nyx.favourites_impl.models

class FavouritesViewModel :
    com.nyx.common_compose.viewmodel.BaseViewModel<FavouritesViewState, FavouritesViewAction, FavouritesViewEvent>(
        initialState = FavouritesViewState()
    ) {

    override fun obtainEvent(viewEvent: FavouritesViewEvent) {
        when (viewEvent) {
            is FavouritesViewEvent.OnBackClicked -> viewAction = FavouritesViewAction.Back
            is FavouritesViewEvent.OnTabClicked -> setTab(viewEvent.tabIndex)
            is FavouritesViewEvent.OnProductClicked -> viewAction = FavouritesViewAction.OpenProductCard
            is FavouritesViewEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun setTab(selectedTabIndex: Int) {
        viewState = viewState.copy(currentSelectedTab = TabType.values()[selectedTabIndex])
    }
}