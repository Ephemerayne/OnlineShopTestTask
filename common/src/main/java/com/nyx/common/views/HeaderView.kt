package com.nyx.common.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeaderView(
    title: String? = null,
    onBackArrowClick: () -> Unit,
    onShareIconClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
                .clickable(onClick = onBackArrowClick),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material.Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = ""
            )
        }

        title?.let {
            HorizontalSpacer(width = 16.dp)
            Text(text = title)
        }

        onShareIconClick?.let {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .clickable(onClick = onShareIconClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Share,
                    contentDescription = ""
                )
            }
        }
    }
}
