package com.nyx.registration_compose.navigation

import androidx.compose.runtime.Composable
import com.nyx.common_compose.viewmodel.observeAction
import com.nyx.registration_api.navigation.LaunchScreenNavigation
import com.nyx.registration_impl.LaunchViewModel
import com.nyx.registration_impl.models.launch.LaunchViewAction
import com.nyx.registration_impl.models.launch.LaunchViewEvent

@Composable
fun launchActionNavigation(
    viewModel: LaunchViewModel,
    navigation: LaunchScreenNavigation,
) {
    viewModel.observeAction(LaunchViewEvent.ActionInvoked) { action ->
        when (action) {
            is LaunchViewAction.OpenCatalog -> {
                navigation.openCatalog()
            }

            is LaunchViewAction.OpenRegistration -> {
                navigation.openRegistration()
            }
        }
    }
}