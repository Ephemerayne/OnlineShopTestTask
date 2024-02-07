package com.nyx.registration_impl

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.nyx.common_api.models.UserEntity
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.common_data.local.user.UserStorage
import com.nyx.common_data.repository.user.UserRepositoryImpl
import com.nyx.common_impl.utils.isCyrillicInput
import com.nyx.registration_impl.models.RegistrationViewAction
import com.nyx.registration_impl.models.RegistrationViewEvent
import com.nyx.registration_impl.models.RegistrationViewState
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val sharedPreferences: SharedPreferences,
) :
    BaseViewModel<RegistrationViewState, RegistrationViewAction, RegistrationViewEvent>(
        initialState = RegistrationViewState()
    ) {

    private val userRepository = UserRepositoryImpl(UserStorage(sharedPreferences))

    init {
        observeUserData()
    }

    override fun obtainEvent(viewEvent: RegistrationViewEvent) {
        when (viewEvent) {
            is RegistrationViewEvent.OnNameChanged -> onNameTextInputChange(viewEvent.input)
            is RegistrationViewEvent.OnSurnameChanged -> onSurnameTextInputChange(viewEvent.input)
            is RegistrationViewEvent.OnPhoneNumberChanged -> onPhoneNumberTextInputChange(viewEvent.input)
            is RegistrationViewEvent.OnClearNameClicked -> clearNameField()
            is RegistrationViewEvent.OnClearSurnameClicked -> clearSurnameField()
            is RegistrationViewEvent.OnClearPhoneNumberClicked -> clearPhoneNumberField()
            is RegistrationViewEvent.OnEnterButtonClicked -> login()
            is RegistrationViewEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun onNameTextInputChange(input: String) {
        viewState = viewState.copy(
            name = input
        )
        validateInput()
    }

    private fun onSurnameTextInputChange(input: String) {
        viewState = viewState.copy(
            surname = input
        )
        validateInput()
    }

    private fun onPhoneNumberTextInputChange(input: String) {
        viewState = viewState.copy(
            phoneNumber = input
        )
        validateInput()
    }

    private fun clearNameField() {
        viewState = viewState.copy(name = "")
    }

    private fun clearSurnameField() {
        viewState = viewState.copy(surname = "")
    }

    private fun clearPhoneNumberField() {
        viewState = viewState.copy(phoneNumber = "")
    }

    private fun login() {
        val user = UserEntity(viewState.name, viewState.surname, viewState.phoneNumber)
        userRepository.setUserData(user)
    }

    private fun observeUserData() {
        viewModelScope.launch {
            userRepository.getUserData().collect {
                if (it != null) {
                    viewAction = RegistrationViewAction.OpenCatalog
                }

                viewState = viewState.copy(isUserDataReceived = true)
            }
        }
    }

    private fun validateInput() {
        val numberStartsWithSeven =
            viewState.phoneNumber.isNotBlank() && viewState.phoneNumber.startsWith("7")

        val isValidNumberLength = if (numberStartsWithSeven) {
            viewState.phoneNumber.length == 11
        } else {
            viewState.phoneNumber.length == 10
        }

        val isInputValid =
            isCyrillicLetterInput(viewState.name) &&
                    isCyrillicLetterInput(viewState.surname) &&
                    isValidNumberLength

        viewState = viewState.copy(isInputValid = isInputValid)
    }

    private fun isCyrillicLetterInput(input: String): Boolean {
        if (input.isBlank()) return false

        return isCyrillicInput(input)
    }
}