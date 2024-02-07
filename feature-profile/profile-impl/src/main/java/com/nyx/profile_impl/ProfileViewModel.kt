package com.nyx.profile_impl

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.common_data.local.FavouriteProductStorage
import com.nyx.common_data.repository.ProductRepositoryImpl
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

    init {
        countFavourites()
    }

    override fun obtainEvent(viewEvent: ProfileViewEvent) {
        viewAction = when (viewEvent) {
            is ProfileViewEvent.OnFavouritesClicked -> ProfileViewAction.NavigateToFavouritesScreen

            is ProfileViewEvent.ActionInvoked -> null
        }
    }

    private fun countFavourites() {
        viewModelScope.launch {
            productRepository.getFavourite().collect {
                viewState = viewState.copy(productCount = it.count())
            }
        }
    }
}