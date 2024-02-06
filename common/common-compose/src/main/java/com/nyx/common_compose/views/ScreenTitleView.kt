package com.nyx.common_compose.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.typography.AppTypography

@Composable
fun ScreenTitleView(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = text,
        textAlign = TextAlign.Center,
        style = AppTypography.title1
    )
}