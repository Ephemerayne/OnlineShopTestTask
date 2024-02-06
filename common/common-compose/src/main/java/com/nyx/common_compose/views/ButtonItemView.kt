package com.nyx.common_compose.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography

@Composable
fun ButtonItemView(
    leadingIcon: Painter? = null,
    title: String,
    subtitle: String? = null,
    trailingIcon: Painter = painterResource(R.drawable.right_arrow_item_icon),
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.background_light_gray)),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = leadingIcon,
                    contentDescription = null
                )
            }
            Column {
                Text(text = title, style = AppTypography.title2)
                subtitle?.let {
                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = subtitle,
                        color = Color.Gray,
                        style = AppTypography.caption1
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = trailingIcon,
                contentDescription = null
            )
        }
    }
}