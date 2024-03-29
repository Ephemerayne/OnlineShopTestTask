package com.nyx.registration_impl

import androidx.lifecycle.viewModelScope
import com.nyx.common_api.models.UserEntity
import com.nyx.common_api.repository.user.UserRepository
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.common_impl.utils.PhoneNumberDefaults
import com.nyx.common_impl.utils.isCyrillicInput
import com.nyx.registration_impl.models.registration.RegistrationViewAction
import com.nyx.registration_impl.models.registration.RegistrationViewEvent
import com.nyx.registration_impl.models.registration.RegistrationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository,
) :
    BaseViewModel<RegistrationViewState, RegistrationViewAction, RegistrationViewEvent>(
        initialState = RegistrationViewState()
    ) {

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
        val availableInputLength = if (input.isNotEmpty() && input.startsWith("7")) {
            PhoneNumberDefaults.INPUT_LENGTH + 1
        } else {
            PhoneNumberDefaults.INPUT_LENGTH
        }
        val pattern = Regex("[0-9]*")
        if (input.length <= availableInputLength && input.matches(pattern)) {
            viewState = viewState.copy(
                phoneNumber = input
            )
        }

        validateInput()
    }

    private fun clearNameField() {
        viewState = viewState.copy(name = "")

        validateInput()
    }

    private fun clearSurnameField() {
        viewState = viewState.copy(surname = "")

        validateInput()
    }

    private fun clearPhoneNumberField() {
        viewState = viewState.copy(phoneNumber = "")

        validateInput()
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
        val isInputValid =
            isCyrillicLetterInput(viewState.name) &&
                    isCyrillicLetterInput(viewState.surname) &&
                    isValidPhoneNumber(viewState.phoneNumber)

        viewState = viewState.copy(isInputValid = isInputValid)
    }

    private fun isCyrillicLetterInput(input: String): Boolean {
        if (input.isBlank()) return false

        return isCyrillicInput(input)
    }
    private fun isValidPhoneNumber(input: String): Boolean {
        if (input.isBlank()) return false

        val numberStartsWithSeven =
            viewState.phoneNumber.isNotBlank() && viewState.phoneNumber.startsWith("7")

        return if (numberStartsWithSeven) {
            viewState.phoneNumber.length == 11
        } else {
            viewState.phoneNumber.length == 10
        }
    }
}