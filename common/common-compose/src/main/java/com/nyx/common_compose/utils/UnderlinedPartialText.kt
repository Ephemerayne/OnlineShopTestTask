package com.nyx.common_compose.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

fun underlinedPartialText(text: String, underlinedText: String): AnnotatedString {
    return buildAnnotatedString {
        val startIndexOfUnderlinedText = text.indexOf(underlinedText)
        val endIndexOfUnderlinedText = startIndexOfUnderlinedText + underlinedText.length

        append(text.substring(0, startIndexOfUnderlinedText))

        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append(text.substring(startIndexOfUnderlinedText, endIndexOfUnderlinedText))
        }

        if (endIndexOfUnderlinedText < text.length) {
            append(text.substring(endIndexOfUnderlinedText))
        }
    }
}