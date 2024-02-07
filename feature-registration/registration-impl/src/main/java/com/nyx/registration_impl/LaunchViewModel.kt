package com.nyx.registration_impl

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.common_data.local.user.UserStorage
import com.nyx.common_data.repository.user.UserRepositoryImpl
import com.nyx.registration_impl.models.launch.LaunchViewAction
import com.nyx.registration_impl.models.launch.LaunchViewEvent
import kotlinx.coroutines.launch

class LaunchViewModel(
    private val sharedPreferences: SharedPreferences,
) :
    BaseViewModel<Unit, LaunchViewAction, LaunchViewEvent>(
        initialState = Unit
    ) {

    private val repo = UserRepositoryImpl(UserStorage(sharedPreferences))

    init {
        observeUserData()
    }

    override fun obtainEvent(viewEvent: LaunchViewEvent) {}

    private fun observeUserData() {
        viewModelScope.launch {
            repo.getUserData().collect {
                viewAction = if (it == null) {
                    LaunchViewAction.OpenRegistration
                } else {
                    LaunchViewAction.OpenCatalog
                }
            }
        }
    }
}