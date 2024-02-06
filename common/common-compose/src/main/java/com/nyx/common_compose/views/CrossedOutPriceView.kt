package com.nyx.common_compose.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography

@Composable
fun CrossedOutPriceView(
    price: Double,
    textColor: Color = colorResource(id = R.color.text_gray),
    textStyle: TextStyle= AppTypography.elementText,
) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = "$price ₽",
        color = textColor,
        style = textStyle
    )
}