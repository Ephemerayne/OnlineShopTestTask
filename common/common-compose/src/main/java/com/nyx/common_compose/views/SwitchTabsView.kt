package com.nyx.common_compose.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography

@Composable
fun SwitchTabsView(
    selectedTabIndex: Int,
    tabsTitles: com.nyx.common_compose.utils.StableList<String>,
    onTabClick: (index: Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = colorResource(id = R.color.background_light_gray),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .height(48.dp),
        indicator = { /* No indicator */ },
        divider = { /* No divider */ }
    ) {
        tabsTitles.forEachIndexed { index, text ->
            val isSelected = selectedTabIndex == index

            Tab(
                modifier = Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isSelected)
                            Color.White else colorResource(id = R.color.background_light_gray)
                    ),
                selected = isSelected,
                onClick = { onTabClick(index) },
                text = {
                    Text(
                        text = text, color = if (isSelected)
                            Color.Black else Color.Gray,
                        style = AppTypography.title1
                    )
                }
            )
        }
    }
}