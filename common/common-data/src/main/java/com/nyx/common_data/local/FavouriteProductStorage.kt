package com.nyx.common_data.local

import android.content.SharedPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

class FavouriteProductStorage(
    private val sharedPreferences: SharedPreferences,
) {
    fun getFavouriteProductIds(): Flow<List<String>> = callbackFlow<List<String>> {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == FAVOURITE_KEY) {
                    val favourite = sharedPreferences.getStringSet(FAVOURITE_KEY, mutableSetOf())
                    favourite?.let { trySend(it.toList()) }
                }
            }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        if (sharedPreferences.contains(FAVOURITE_KEY)) {
            sharedPreferences.getStringSet(FAVOURITE_KEY, setOf<String>())?.let {
                send(it.toList())
            }
        }
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }.buffer(Channel.UNLIMITED)

    fun putValue(id: String) {
        val favourites = sharedPreferences.getStringSet(FAVOURITE_KEY, mutableSetOf()) ?: mutableSetOf()
        val newFavourites = mutableSetOf<String>().apply {
            addAll(favourites)
            add(id)
        }

        sharedPreferences.edit().putStringSet(FAVOURITE_KEY, newFavourites).apply()
    }

    fun deleteValue(id: String) {
        val favourites = sharedPreferences.getStringSet(FAVOURITE_KEY, mutableSetOf()) ?: mutableSetOf()
        val newFavourites = mutableSetOf<String>().apply {
            addAll(favourites)
            remove(id)
        }

        sharedPreferences.edit().putStringSet(FAVOURITE_KEY, newFavourites).apply()
    }

    companion object {
        const val FAVOURITE_KEY = "FAVOURITE_KEY"
    }
}