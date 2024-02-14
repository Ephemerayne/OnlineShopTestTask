package com.nyx.profile_impl

import androidx.lifecycle.viewModelScope
import com.nyx.common_api.repository.product.ProductRepository
import com.nyx.common_api.repository.user.UserRepository
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.profile_impl.models.ProfileViewAction
import com.nyx.profile_impl.models.ProfileViewEvent
import com.nyx.profile_impl.models.ProfileViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
) : BaseViewModel<ProfileViewState, ProfileViewAction, ProfileViewEvent>(
    initialState = ProfileViewState()
) {

    init {
        countFavourites()
        observeUserData()
    }

    override fun obtainEvent(viewEvent: ProfileViewEvent) {
        when (viewEvent) {
            is ProfileViewEvent.OnFavouritesClicked -> viewAction =
                ProfileViewAction.NavigateToFavouritesScreen

            is ProfileViewEvent.OnExitClicked -> logout()
            is ProfileViewEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun countFavourites() {
        viewModelScope.launch {
            productRepository.getFavourite().collect {
                viewState = viewState.copy(productCount = it.count())
            }
        }
    }

    private fun logout() {
        userRepository.clearUserData()

        viewAction = ProfileViewAction.NavigateToRegistrationScreen
    }

    private fun observeUserData() {
        viewModelScope.launch {
            userRepository.getUserData().collect {
                if (it == null) {
                    // viewAction = ProfileViewAction.NavigateToRegistrationScreen
                } else {
                    viewState = viewState.copy(userEntity = it)
                }
            }
        }
    }
}