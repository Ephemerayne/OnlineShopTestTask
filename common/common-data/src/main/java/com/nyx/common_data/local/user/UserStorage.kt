package com.nyx.common_data.local.user

import android.content.SharedPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

class UserStorage(private val sharedPreferences: SharedPreferences,
) {
    fun getUser(): Flow<String?> = callbackFlow<String> {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == USER_KEY) {
                    val user = sharedPreferences.getString(USER_KEY, "")
                    user?.let { trySend(it) }
                }
            }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        if (sharedPreferences.contains(USER_KEY)) {
            sharedPreferences.getString(USER_KEY, "")?.let {
                send(it)
            }
        }
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }.buffer(Channel.UNLIMITED)

    fun setUserData(value: String) {
        val user = sharedPreferences.getString(USER_KEY, value)
        sharedPreferences.edit().putString(USER_KEY, user).apply()
    }

    fun deleteUserData() {
        val user = sharedPreferences.getString(USER_KEY, "")
        sharedPreferences.edit().putString(USER_KEY, user).apply()
    }

    companion object {
        const val USER_KEY = "USER_KEY"
    }
}