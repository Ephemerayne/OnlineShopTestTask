package com.nyx.common_api.repository.user

import com.nyx.common_api.models.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserData(): Flow<UserEntity?>
    fun setUserData(userEntity: UserEntity)
    fun clearUserData()
}