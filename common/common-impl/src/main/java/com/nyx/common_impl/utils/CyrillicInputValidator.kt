package com.nyx.common_impl.utils

fun isCyrillicInput(input: String): Boolean {
    val pattern = Regex("[а-яА-ЯёЁ]*")
    return input.matches(pattern)
}