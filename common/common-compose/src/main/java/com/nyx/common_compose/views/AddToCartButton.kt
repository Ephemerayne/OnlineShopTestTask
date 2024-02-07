package com.nyx.common_compose.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography

@Composable
fun AddToCartButton(
    modifier: Modifier = Modifier,
    newPrice: String,
    oldPrice: String,
    unit: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(51.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NewPriceView(
                price = newPrice,
                unit= unit,
                textStyle = AppTypography.buttonText2.copy(Color.White)
            )
            HorizontalSpacer(width = 4.dp)
            CrossedOutPriceView(
                price = oldPrice,
                unit = unit,
                textColor = colorResource(id = R.color.light_pink),
                textStyle = AppTypography.caption1
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.add_to_cart_button_text),
                color = Color.White,
                style = AppTypography.buttonText2
            )
        }
    }
}