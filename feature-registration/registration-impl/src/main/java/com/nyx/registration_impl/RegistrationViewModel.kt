package com.nyx.registration_impl

import com.nyx.registration_impl.models.RegistrationViewAction
import com.nyx.registration_impl.models.RegistrationViewEvent
import com.nyx.registration_impl.models.RegistrationViewState

class RegistrationViewModel :
    com.nyx.common_compose.viewmodel.BaseViewModel<RegistrationViewState, RegistrationViewAction, RegistrationViewEvent>(
        initialState = RegistrationViewState()
    ) {

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
    }

    private fun onSurnameTextInputChange(input: String) {
        viewState = viewState.copy(
            surname = input
        )
    }

    private fun onPhoneNumberTextInputChange(input: String) {
        viewState = viewState.copy(
            phoneNumber = input
        )
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
        viewAction = RegistrationViewAction.OpenCatalog
    }
}