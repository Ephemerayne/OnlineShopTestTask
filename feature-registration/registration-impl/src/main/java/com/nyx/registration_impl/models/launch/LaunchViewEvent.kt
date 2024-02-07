package com.nyx.registration_impl.models.launch

sealed class LaunchViewEvent {
    object ActionInvoked: LaunchViewEvent()
}
