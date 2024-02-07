package com.nyx.registration_impl.models.launch

sealed class LaunchViewAction {
    object OpenCatalog : LaunchViewAction()
    object OpenRegistration : LaunchViewAction()
}
