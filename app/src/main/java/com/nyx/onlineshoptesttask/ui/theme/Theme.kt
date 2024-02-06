package com.nyx.onlineshoptesttask.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColors(
    primary = Pink,
)

@Composable
fun OnlineShopTestTaskTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = LightColorScheme,
        typography = Typography,
        content = content
    )
}