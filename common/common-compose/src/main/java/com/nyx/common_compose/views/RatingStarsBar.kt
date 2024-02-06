package com.nyx.common_compose.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R


@Composable
fun RatingStarsBar(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        val fullStars = rating.toInt()
        val hasHalfStar = rating % 1 != 0.0
        val totalStars = 5
        val emptyStars = totalStars - fullStars - if (hasHalfStar) 1 else 0

        for (i in 1..fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Full Star",
                modifier = Modifier.size(24.dp),
                tint = colorResource(id = R.color.orange)
            )
        }

        if (hasHalfStar) {
            Icon(
                imageVector = Icons.Filled.List,
                contentDescription = "Half Star",
                modifier = Modifier.size(24.dp),
                tint = colorResource(id = R.color.orange)
            )
        }

        for (i in 0 until emptyStars) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "Empty Star",
                modifier = Modifier.size(24.dp),
                tint = colorResource(id = R.color.orange)
            )
        }
    }
}