package com.nyx.registration_impl.models.registration

sealed class RegistrationViewEvent {
    class OnNameChanged(val input: String): RegistrationViewEvent()
    class OnSurnameChanged(val input: String): RegistrationViewEvent()
    class OnPhoneNumberChanged(val input: String): RegistrationViewEvent()
    object OnClearNameClicked: RegistrationViewEvent()
    object OnClearSurnameClicked: RegistrationViewEvent()
    object OnClearPhoneNumberClicked: RegistrationViewEvent()
    object OnEnterButtonClicked: RegistrationViewEvent()
    object ActionInvoked: RegistrationViewEvent()
}