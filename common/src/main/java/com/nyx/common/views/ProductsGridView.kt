package com.nyx.common.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ProductsGridView(onProductClick: () -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 8.dp),
        content = {
            items(9) { product ->
                ProductItem(onProductClick)
            }
        })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductItem(onProductClick: () -> Unit) {
    val pagerState = rememberPagerState(0)

    Column(
        modifier = Modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(8.dp))
            .clickable(onClick = onProductClick)
    )
    {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(144.dp)
        ) {
            ImagePager(pagerState = pagerState, imagesCount = 4)
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = ""
            )
        }
        VerticalSpacer(height = 4.dp)
        ImagePagerIndicatorView(
            pagerState = pagerState,
            imagesCount = 4,
            indicator = IndicatorSettings(size = 4.dp, paddingBetween = 2.dp)
        )
        VerticalSpacer(height = 4.dp)
        CrossedOutPriceView(price = 140.0)

        //price and sales
        Row(verticalAlignment = Alignment.CenterVertically) {
            NewPriceView(
                modifier = Modifier.padding(start = 4.dp),
                price = 124.0
            )
            HorizontalSpacer(width = 4.dp)
            DiscountChipView(discount = 35)
        }

        // product title and description
        Text(modifier = Modifier.padding(horizontal = 4.dp), text = "PRODUCT")
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = "This is product description bla bla bla This is product description bla bla bla This is product description bla bla bla This is product description bla bla bla",
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        // rating
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = "", tint = Color.Yellow)
            HorizontalSpacer(width = 4.dp)
            Text(text = "4.5", color = Color.Yellow)
            HorizontalSpacer(width = 4.dp)
            Text(text = "(10)", color = Color.LightGray)
        }
        Button(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.End),
            contentPadding = PaddingValues(1.dp),
            shape = RoundedCornerShape(8.dp, 0.dp, 8.dp, 0.dp),
            onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}