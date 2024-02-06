package com.nyx.common_compose.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun AddToCartButton(
    modifier: Modifier = Modifier,
    newPrice: Double,
    oldPrice: Double,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp).height(51.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NewPriceView(price = newPrice, textStyle = TextStyle(color = Color.White))
            HorizontalSpacer(width = 4.dp)
            CrossedOutPriceView(price = oldPrice)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Добавить в корзину", color = Color.White)
        }
    }
}