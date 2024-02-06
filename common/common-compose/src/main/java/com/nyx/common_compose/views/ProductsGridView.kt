package com.nyx.common_compose.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography

@Composable
fun ProductsGridView(
    onProductClick: () -> Unit,
    onFavouriteClick: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 8.dp),
        content = {
            items(9) { product ->
                ProductItem(onProductClick, onFavouriteClick)
            }
        })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductItem(
    onProductClick: () -> Unit,
    onFavouriteClick: () -> Unit,
) {
    val pagerState = rememberPagerState(0)

    Column(
        modifier = Modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .border(
                BorderStroke(1.dp, colorResource(id = R.color.background_light_gray)),
                RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onProductClick)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            ImagePager(pagerState = pagerState, imagesCount = 4)
            FavouriteIconView(onFavouriteClick = onFavouriteClick)
        }
        VerticalSpacer(height = 4.dp)
        ImagePagerIndicatorView(
            pagerState = pagerState,
            imagesCount = 4,
            indicator = IndicatorSettings(
                size = 4.dp,
                paddingBetween = 2.dp,
                selectedColor = colorResource(id = R.color.pink),
                unselectedColor = colorResource(id = R.color.element_light_gray),
            )
        )
        VerticalSpacer(height = 4.dp)
        CrossedOutPriceView(price = 140.0, unit = "Р")
        PriceAndSalesView()
        TitleAndDescriptionView()
        RatingView()
        AddToCartIconButton()
    }
}

@Composable
private fun BoxScope.FavouriteIconView(onFavouriteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .clickable(onClick = onFavouriteClick)
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp),
            painter = painterResource(R.drawable.favourite_icon),
            contentDescription = null
        )
    }
}

@Composable
private fun PriceAndSalesView() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        NewPriceView(
            modifier = Modifier.padding(start = 4.dp),
            price = 124.0,
            unit = "Р",
            textStyle = AppTypography.title2
        )
        HorizontalSpacer(width = 4.dp)
        DiscountChipView(discount = 35)
    }
}

@Composable
private fun TitleAndDescriptionView() {
    Text(
        modifier = Modifier.padding(horizontal = 4.dp),
        text = "PRODUCT",
        style = AppTypography.title3
    )
    Text(
        modifier = Modifier.padding(horizontal = 4.dp),
        text = "Лосьон для тела`ESFOLIO` COENZYME Q 10 Увлажняющий 500 мл",
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        style = AppTypography.caption1
    )
}

@Composable
private fun RatingView() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.rating_icon),
            contentDescription = null,
        )
        HorizontalSpacer(width = 4.dp)
        Text(
            text = "4.5",
            color = colorResource(id = R.color.orange),
            style = AppTypography.elementText
        )
        HorizontalSpacer(width = 4.dp)
        Text(
            text = "(10)",
            color = colorResource(id = R.color.text_gray),
            style = AppTypography.elementText
        )
    }
}

@Composable
private fun ColumnScope.AddToCartIconButton() {
    Button(
        modifier = Modifier
            .size(32.dp)
            .align(Alignment.End),
        contentPadding = PaddingValues(1.dp),
        shape = RoundedCornerShape(8.dp, 0.dp, 8.dp, 0.dp),
        elevation = null,
        onClick = { /* No implementation */ }) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.add_icon),
            contentDescription = null
        )
    }
}