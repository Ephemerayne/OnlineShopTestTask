package com.nyx.common_api.models

data class UserEntity(
    val name: String,
    val surname: String,
    val phoneNumber: String,
) {
    companion object {
        val empty: UserEntity
            get() = UserEntity(
                name = "",
                surname = "",
                phoneNumber = ""
            )
    }
}