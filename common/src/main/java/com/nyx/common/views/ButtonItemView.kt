package com.nyx.common.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ButtonItemView(
    icon: ImageVector? = null,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    imageVector = icon,
                    contentDescription = ""
                )
            }
            Text(text = text)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
        }
    }
}