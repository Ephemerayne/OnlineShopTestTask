package com.nyx.profile_impl

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.common_data.local.product.FavouriteProductStorage
import com.nyx.common_data.local.user.UserStorage
import com.nyx.common_data.repository.product.ProductRepositoryImpl
import com.nyx.common_data.repository.user.UserRepositoryImpl
import com.nyx.profile_impl.models.ProfileViewAction
import com.nyx.profile_impl.models.ProfileViewEvent
import com.nyx.profile_impl.models.ProfileViewState
import kotlinx.coroutines.launch

class ProfileViewModel(
    sharedPreferences: SharedPreferences,
) : BaseViewModel<ProfileViewState, ProfileViewAction, ProfileViewEvent>(
    initialState = ProfileViewState()
) {

    private val productRepository =
        ProductRepositoryImpl(FavouriteProductStorage(sharedPreferences))
    private val userRepository = UserRepositoryImpl(UserStorage(sharedPreferences))

    init {
        countFavourites()
        observeUserData()
    }

    override fun obtainEvent(viewEvent: ProfileViewEvent) {
        when (viewEvent) {
            is ProfileViewEvent.OnFavouritesClicked -> viewAction =
                ProfileViewAction.NavigateToFavouritesScreen

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