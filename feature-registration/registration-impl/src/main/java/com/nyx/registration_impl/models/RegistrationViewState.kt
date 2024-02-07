package com.nyx.registration_impl.models

data class RegistrationViewState(
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
    val isInputValid: Boolean = false
)