package com.nyx.common_compose.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun NewPriceView(
    modifier: Modifier = Modifier,
    price: Double,
    textStyle: TextStyle = TextStyle(),
) {
    Text(
        modifier = modifier,
        text = "$price ₽",
        style = textStyle
    )
}