package com.nyx.common_compose.utils

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
data class StableList<T>(private val delegate: List<T>) : List<T> by delegate {
    val mutableState = mutableStateOf(size)
}

inline fun <T> List<T>.toStable(): StableList<T> = if (this is StableList<T>) this else StableList(this)


@Stable
data class StableMap<K, V>(private val delegate: Map<K, V>) : Map<K, V> by delegate {
    val mutableState = mutableStateOf(size)
}

inline fun <K, V> Map<K, V>.toStable(): StableMap<K, V> = if (this is StableMap<K, V>) this else StableMap(this)