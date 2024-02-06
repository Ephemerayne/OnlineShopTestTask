package com.nyx.profile_impl

import com.nyx.profile_impl.models.ProfileViewAction
import com.nyx.profile_impl.models.ProfileViewEvent
import com.nyx.profile_impl.models.ProfileViewState

class ProfileViewModel : com.nyx.common_compose.viewmodel.BaseViewModel<ProfileViewState, ProfileViewAction, ProfileViewEvent>(
    initialState = ProfileViewState()
) {

    override fun obtainEvent(viewEvent: ProfileViewEvent) {
        viewAction = when (viewEvent) {
            is ProfileViewEvent.OnFavouritesClicked -> ProfileViewAction.NavigateToFavouritesScreen

            is ProfileViewEvent.ActionInvoked -> null
        }
    }
}