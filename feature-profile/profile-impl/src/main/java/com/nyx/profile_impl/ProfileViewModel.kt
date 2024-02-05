package com.nyx.profile_impl

import com.nyx.common.viewmodel.BaseViewModel
import com.nyx.profile_impl.models.ProfileViewAction
import com.nyx.profile_impl.models.ProfileViewEvent
import com.nyx.profile_impl.models.ProfileViewState

class ProfileViewModel : BaseViewModel<ProfileViewState, ProfileViewAction, ProfileViewEvent>(
    initialState = ProfileViewState()
) {

    override fun obtainEvent(viewEvent: ProfileViewEvent) {
        viewAction = when (viewEvent) {
            is ProfileViewEvent.OnFavouritesClicked -> ProfileViewAction.NavigateToFavouritesScreen

            is ProfileViewEvent.ActionInvoked -> null
        }
    }
}