package com.nyx.common.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun CrossedOutPriceView(price: Double) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = "$price ₽",
        color = Color.LightGray,
        style = TextStyle(textDecoration = TextDecoration.LineThrough)
    )
}