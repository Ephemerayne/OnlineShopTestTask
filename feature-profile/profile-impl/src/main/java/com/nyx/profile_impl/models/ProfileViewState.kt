package com.nyx.profile_impl.models

import com.nyx.common_api.models.UserEntity

data class ProfileViewState(
    val productCount: Int = 0,
    val userEntity: UserEntity = UserEntity.empty,
)