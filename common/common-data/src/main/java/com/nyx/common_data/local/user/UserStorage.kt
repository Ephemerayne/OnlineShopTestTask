package com.nyx.common_data.local.user

import android.content.SharedPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {
    fun getUser(): Flow<String?> = callbackFlow {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == USER_KEY) {
                    val user = sharedPreferences.getString(USER_KEY, null)
                    trySend(user)
                }
            }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        send(
            if (sharedPreferences.contains(USER_KEY)) {
                sharedPreferences.getString(USER_KEY, null)
            } else {
                null
            }
        )

        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }.buffer(Channel.UNLIMITED)

    fun setUserData(value: String) {
        val user = sharedPreferences.getString(USER_KEY, value)
        sharedPreferences.edit().putString(USER_KEY, user).apply()
    }

    fun deleteUserData() {
        sharedPreferences.edit().putString(USER_KEY, null).apply()
    }

    companion object {
        const val USER_KEY = "USER_KEY"
    }
}