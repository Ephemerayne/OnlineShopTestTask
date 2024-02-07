package com.nyx.registration_compose.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nyx.common_api.constant.Constants
import com.nyx.common_compose.viewmodel.viewModelFactory
import com.nyx.registration_api.navigation.LaunchScreenNavigation
import com.nyx.registration_compose.navigation.launchActionNavigation
import com.nyx.registration_impl.LaunchViewModel


@Composable
fun LauncherScreen(
    screenNavigation: LaunchScreenNavigation
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    val viewModel: LaunchViewModel = viewModel(factory = viewModelFactory {
        LaunchViewModel(sharedPref)
    })

    Box(modifier = Modifier.fillMaxSize())

    launchActionNavigation(viewModel, screenNavigation)
}