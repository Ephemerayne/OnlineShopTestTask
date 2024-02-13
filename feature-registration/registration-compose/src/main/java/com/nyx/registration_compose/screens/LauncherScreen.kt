package com.nyx.registration_compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyx.registration_api.navigation.LaunchScreenNavigation
import com.nyx.registration_compose.navigation.launchActionNavigation
import com.nyx.registration_impl.LaunchViewModel

@Composable
fun LauncherScreen(
    screenNavigation: LaunchScreenNavigation,
    viewModel: LaunchViewModel = hiltViewModel(),
) {
    Box(modifier = Modifier.fillMaxSize())

    launchActionNavigation(viewModel, screenNavigation)
}