package com.nyx.common_compose.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography

@Composable
fun CrossedOutPriceView(
    price: Double,
    unit: String,
    textColor: Color = colorResource(id = R.color.text_gray),
    textStyle: TextStyle = AppTypography.elementText,
) {
    Layout(
        modifier = Modifier.wrapContentSize(),
        content = {
            Text(
                text = stringResource(R.string.price_with_unit_text, price, unit),
                color = textColor,
                style = textStyle,
                modifier = Modifier
                    .layoutId("text")
                    .padding(start = 4.dp)
            )
            Divider(
                color = colorResource(R.color.text_gray),
                thickness = 1.dp,
                modifier = Modifier
                    .layoutId("divider")
                    .rotate(-5f)
            )
        }
    ) { measurables, constraints ->
        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)
        val dividerPlaceable = measurables.first { it.layoutId == "divider" }
            .measure(Constraints.fixedWidth(textPlaceable.width))

        layout(textPlaceable.width, textPlaceable.height) {
            textPlaceable.placeRelative(0, 0)
            val dividerY = textPlaceable.height / 2 - dividerPlaceable.height / 2
            dividerPlaceable.placeRelative(0, dividerY)
        }
    }
}