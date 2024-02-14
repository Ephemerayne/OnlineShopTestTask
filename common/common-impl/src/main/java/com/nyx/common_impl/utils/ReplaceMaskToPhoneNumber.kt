package com.nyx.common_impl.utils

object PhoneNumberDefaults {
    const val MASK = "+7 XXX XXX-XX-XX"
    const val INPUT_LENGTH = 10
}

fun replaceMaskToPhoneNumber(number: String): String {
    val mask = PhoneNumberDefaults.MASK
    var index = 0

    return mask
        .replace(oldChar = '-', newChar = ' ')
        .map { char ->
        if (char == 'X') {
            if (index < number.length) {
                number[index++]
            } else {
                char
            }
        } else {
            char
        }
    }.joinToString("")
}