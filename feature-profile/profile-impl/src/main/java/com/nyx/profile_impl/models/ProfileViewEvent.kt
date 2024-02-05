package com.nyx.profile_impl.models

sealed class ProfileViewEvent {
    object ActionInvoked: ProfileViewEvent()
}
