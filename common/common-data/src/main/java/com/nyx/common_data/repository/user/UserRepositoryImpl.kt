package com.nyx.common_data.repository.user

import com.google.gson.Gson
import com.nyx.common_api.models.UserEntity
import com.nyx.common_api.repository.user.UserRepository
import com.nyx.common_data.local.user.UserStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userStorage: UserStorage,
) : UserRepository {

    override fun getUserData(): Flow<UserEntity?> {
        return try {
            userStorage.getUser().map { Gson().fromJson(it, UserEntity::class.java) }
        } catch (e: Exception) {
            MutableStateFlow(null)
        }
    }

    override fun setUserData(userEntity: UserEntity) {
        userStorage.setUserData(Gson().toJson(userEntity))
    }

    override fun clearUserData() {
        userStorage.deleteUserData()
    }
}