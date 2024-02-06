package com.nyx.common_compose.views

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HideOrShowView(
    isViewVisible: Boolean,
    onHideOrShowClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .offset((-12).dp)
            .noRippleClickable(onClick = onHideOrShowClick)
            .padding(12.dp),
        text = if (isViewVisible) "Скрыть" else "Подробнее",
        color = Color.LightGray,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ExpandOrReduceView(
    isViewExpanded: Boolean,
    onHideOrShowClick: (() -> Unit)? = null,
) {
    onHideOrShowClick?.let {
        Text(
            modifier = Modifier
                .offset((-12).dp)
                .noRippleClickable(onClick = onHideOrShowClick)
                .padding(12.dp),
            text = if (isViewExpanded) "Скрыть" else "Подробнее",
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
    }
}