package com.nyx.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDividerView() {
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(0.5.dp)
        .background(Color.LightGray))
}