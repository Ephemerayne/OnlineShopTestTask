package com.nyx.registration_compose.navigation

import androidx.compose.runtime.Composable
import com.nyx.common_compose.viewmodel.observeAction
import com.nyx.registration_api.navigation.RegistrationScreenNavigation
import com.nyx.registration_impl.RegistrationViewModel
import com.nyx.registration_impl.models.registration.RegistrationViewAction
import com.nyx.registration_impl.models.registration.RegistrationViewEvent

@Composable
fun registrationActionNavigation(
    viewModel: RegistrationViewModel,
    navigation: RegistrationScreenNavigation,
) {
    viewModel.observeAction(RegistrationViewEvent.ActionInvoked) { action ->
        when (action) {
            is RegistrationViewAction.OpenCatalog -> {
                navigation.openCatalog()
            }
        }
    }
}