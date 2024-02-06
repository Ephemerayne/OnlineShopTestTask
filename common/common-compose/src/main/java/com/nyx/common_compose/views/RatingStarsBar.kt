package com.nyx.common_compose.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.nyx.common_compose.R


@Composable
fun RatingStarsBar(rating: Double) {
    Row {
        val fullStars = rating.toInt()
        val hasHalfStar = rating % 1 != 0.0
        val totalStars = 5
        val emptyStars = totalStars - fullStars - if (hasHalfStar) 1 else 0

        for (i in 1..fullStars) {
            Image(
                painter = painterResource(R.drawable.full_star_icon),
                contentDescription = "Full Star"
            )
        }

        if (hasHalfStar) {
            Image(
                painter = painterResource(R.drawable.half_star_icon),
                contentDescription = "Half Star"
            )
        }

        for (i in 0 until emptyStars) {
            Image(
                painter = painterResource(R.drawable.empty_star_icon),
                contentDescription = "Empty Star"
            )
        }
    }
}