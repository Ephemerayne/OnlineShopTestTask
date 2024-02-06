package com.nyx.common_compose.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDividerView(thickness: Dp = 1.dp) {
    Divider(modifier = Modifier.fillMaxWidth(), thickness = thickness, color = Color.LightGray)
}