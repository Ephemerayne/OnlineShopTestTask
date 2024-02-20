package com.nyx.common_api.common

sealed class ProgressState<out T> {
    object Progress : ProgressState<Nothing>()
    object Failure : ProgressState<Nothing>()
    class Success<out T>(val value: T) : ProgressState<T>()
}