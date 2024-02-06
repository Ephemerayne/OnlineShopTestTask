package com.nyx.common_compose.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    imagesCount: Int,
) {
    Box(modifier = modifier.fillMaxSize()) {


        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageCount = imagesCount
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(368.dp)
                    .background(Color(Random.nextInt()))
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePagerIndicatorView(
    pagerState: PagerState,
    imagesCount: Int,
    indicator: IndicatorSettings = IndicatorSettings(),
) {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(imagesCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) indicator.selectedColor else indicator.unselectedColor
            Box(
                modifier = Modifier
                    .padding(indicator.paddingBetween)
                    .clip(CircleShape)
                    .background(color)
                    .size(indicator.size)
            )
        }
    }
}

data class IndicatorSettings(
    val size: Dp = 6.dp,
    val paddingBetween: Dp = 4.dp,
    val selectedColor: Color = Color.DarkGray,
    val unselectedColor: Color = Color.LightGray,
)