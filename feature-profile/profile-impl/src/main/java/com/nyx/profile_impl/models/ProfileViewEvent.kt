package com.nyx.profile_impl.models

sealed class ProfileViewEvent {
    object OnFavouritesClicked: ProfileViewEvent()
    object OnExitClicked: ProfileViewEvent()
    object ActionInvoked: ProfileViewEvent()
}
