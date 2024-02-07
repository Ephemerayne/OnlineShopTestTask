package com.nyx.common_compose.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.nyx.common_compose.R

@Composable
fun NewPriceView(
    modifier: Modifier = Modifier,
    price: String,
    unit: String,
    textStyle: TextStyle = TextStyle(),
) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.price_with_unit_text, price, unit),
        style = textStyle
    )
}