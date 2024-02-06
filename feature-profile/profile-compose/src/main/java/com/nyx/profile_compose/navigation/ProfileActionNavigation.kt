package com.nyx.profile_compose.navigation

import androidx.compose.runtime.Composable
import com.nyx.common_compose.viewmodel.observeAction
import com.nyx.profile_api.navigation.ProfileScreenNavigation
import com.nyx.profile_impl.ProfileViewModel
import com.nyx.profile_impl.models.ProfileViewAction
import com.nyx.profile_impl.models.ProfileViewEvent

@Composable
fun profileActionNavigation(
    viewModel: ProfileViewModel,
    screenNavigation: ProfileScreenNavigation,
) {
    viewModel.observeAction(ProfileViewEvent.ActionInvoked) { action ->
        when (action) {
            is ProfileViewAction.NavigateToFavouritesScreen -> {
                screenNavigation.openFavourites()
            }
        }
    }
}