package com.nyx.common_compose.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography

@Composable
fun DiscountChipView(discount: Int) {
    Card(
        backgroundColor = colorResource(id = R.color.pink),
        shape = RoundedCornerShape(4.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            text = stringResource(R.string.discount_text, discount),
            color = Color.White,
            textAlign = TextAlign.Center,
            style = AppTypography.elementText
        )
    }
}