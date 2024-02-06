package com.nyx.common_compose.views

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DiscountChipView(discount: Int) {
    Card(
        modifier = Modifier
            .width(34.dp)
            .height(16.dp),
        backgroundColor = Color.Magenta,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = "-$discount%",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}