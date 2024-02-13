package com.nyx.registration_impl

import androidx.lifecycle.viewModelScope
import com.nyx.common_api.repository.user.UserRepository
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.registration_impl.models.launch.LaunchViewAction
import com.nyx.registration_impl.models.launch.LaunchViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel<Unit, LaunchViewAction, LaunchViewEvent>(
        initialState = Unit
    ) {

    init {
        observeUserData()
    }

    override fun obtainEvent(viewEvent: LaunchViewEvent) {}

    private fun observeUserData() {
        viewModelScope.launch {
            userRepository.getUserData().collect {
                viewAction = if (it == null) {
                    LaunchViewAction.OpenRegistration
                } else {
                    LaunchViewAction.OpenCatalog
                }
            }
        }
    }
}