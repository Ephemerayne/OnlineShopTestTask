package com.nyx.product_card_compose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common_api.models.Info
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography
import com.nyx.common_compose.viewmodel.rememberEvent
import com.nyx.common_compose.views.*
import com.nyx.product_card_api.navigation.ProductCardScreenNavigation
import com.nyx.product_card_compose.navigation.productCardActionNavigation
import com.nyx.product_card_impl.ProductCardViewModel
import com.nyx.product_card_impl.models.ProductCardViewEvent
import com.nyx.product_card_impl.models.ProductCardViewState

@Composable
fun ProductCardScreen(
    viewModel: ProductCardViewModel = viewModel(),
    screenNavigation: ProductCardScreenNavigation,
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val onHideOrShowDescriptionClick =
        viewModel.rememberEvent(ProductCardViewEvent.HideOrShowDescriptionClicked)

    val onHideOrShowIngredientsClick =
        viewModel.rememberEvent(ProductCardViewEvent.HideOrShowIngredientsClicked)

    val onIngredientsLinesCountMeasured = viewModel.rememberEvent<Boolean, _> {
        ProductCardViewEvent.IngredientsTextLinesCountMeasured(it)
    }

    ProductCardView(
        viewState = viewState,
        onBackArrowClick = { screenNavigation.back() },
        onHideOrShowDescriptionClick = onHideOrShowDescriptionClick,
        onHideOrShowIngredientsClick = onHideOrShowIngredientsClick,
        onIngredientsLinesCountMeasured = onIngredientsLinesCountMeasured
    )

    productCardActionNavigation(viewModel = viewModel, screenNavigation = screenNavigation)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductCardView(
    viewState: ProductCardViewState,
    onBackArrowClick: () -> Unit,
    onHideOrShowDescriptionClick: () -> Unit,
    onHideOrShowIngredientsClick: () -> Unit,
    onIngredientsLinesCountMeasured: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(0)


    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            HeaderView(
                onBackArrowClick = onBackArrowClick,
                onShareIconClick = {})
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                VerticalSpacer(height = 12.dp)
                ImagePagerView(pagerState = pagerState, 4)
                VerticalSpacer(height = 8.dp)
                ImagePagerIndicatorView(
                    pagerState = pagerState,
                    imagesCount = 4,
                    indicator = IndicatorSettings(
                        selectedColor = colorResource(id = R.color.pink),
                        unselectedColor = colorResource(id = R.color.element_light_gray)
                    ),
                )
                VerticalSpacer(height = 8.dp)
                ProductTitleView()
                VerticalSpacer(height = 12.dp)
                AvailableStockView()
                VerticalSpacer(height = 12.dp)
                HorizontalDividerView(thickness = 0.5.dp)
                VerticalSpacer(height = 12.dp)
                RatingView(rating = 3.5, reviews = 6)
                VerticalSpacer(height = 16.dp)
                PriceView(150.0, 300.0, 50)
                VerticalSpacer(height = 16.dp)
                DescriptionView(
                    brand = "BRAND",
                    description = "This is decription product This is decription product This is decription product This is decription product This is decription product This is decription productThis is decription product This is decription productThis is decription product",
                    isDescriptionVisible = viewState.isDescriptionVisible,
                    onClick = {/* No implementation */ },
                    onHideOrShowDescriptionClick = onHideOrShowDescriptionClick
                )
                VerticalSpacer(height = 24.dp)
                CharacteristicsView(
                    characteristics = listOf(
                        Info("title 1", "value 1"),
                        Info("title 2", "value 2"),
                        Info("title 3", "value 3"),
                        Info("title 4", "value 4")
                    )
                )
                VerticalSpacer(height = 20.dp)
                IngredientsView(
                    description = "ct  product ingredients product efefef nts prodgggggggggggggggggg rfffffffffffffffffffffd df fg dfg dfuct  product ingredients produ nts product  product ingredients produ",
                    isIngredientsExpanded = viewState.isIngredientsExpanded,
                    onHideOrShowIngredientsClick = onHideOrShowIngredientsClick,
                    onIngredientsLinesCountMeasured = onIngredientsLinesCountMeasured,
                    isIngredientsTextHasMoreThanTwoLines = viewState.isIngredientsTextHasMoreThanTwoLines
                )
                VerticalSpacer(height = 80.dp)
            }
        }
        AddToCartButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            newPrice = 150.0,
            oldPrice = 300.0,
            onClick = {/* No implementation */ }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImagePagerView(
    pagerState: PagerState,
    imagesCount: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ImagePager(
            pagerState = pagerState,
            imagesCount = imagesCount
        )
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp),
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = ""
        )
        Icon(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 4.dp, bottom = 16.dp),
            imageVector = Icons.Default.Info,
            contentDescription = ""
        )
    }
}

@Composable
private fun ProductTitleView() {
    Text(
        text = "BRAND",
        color = colorResource(id = R.color.text_gray),
        style = AppTypography.title1
    )
    VerticalSpacer(height = 12.dp)
    Text(
        text = "Пенка для умывания`A`PIEU` `DEEP CLEAN` 200 мл",
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        style = AppTypography.largeTitle,
    )
}

@Composable
private fun AvailableStockView() {
    Text(
        text = "Доступно для заказа 30 штук",
        color = colorResource(id = R.color.text_gray),
        style = AppTypography.text1
    )
}

@Composable
private fun RatingView(rating: Double, reviews: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RatingStarsBar(rating = rating)
        HorizontalSpacer(width = 8.dp)
        Text(
            text = rating.toString(),
            color = colorResource(id = R.color.text_gray),
            style = AppTypography.text1
        )
        HorizontalSpacer(width = 4.dp)
        Text(
            text = "· $reviews отзыва",
            color = colorResource(id = R.color.text_gray),
            style = AppTypography.text1
        )
    }
}

@Composable
private fun PriceView(newPrice: Double, oldPrice: Double, discount: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        NewPriceView(price = newPrice, textStyle = AppTypography.priceText)
        HorizontalSpacer(width = 12.dp)
        CrossedOutPriceView(price = oldPrice, textStyle = AppTypography.text1)
        HorizontalSpacer(width = 12.dp)
        DiscountChipView(discount = discount)
    }
}

@Composable
private fun DescriptionView(
    brand: String,
    description: String,
    isDescriptionVisible: Boolean,
    onClick: () -> Unit,
    onHideOrShowDescriptionClick: () -> Unit,
) {
    TitleView(text = "Описание")

    if (isDescriptionVisible) {
        VerticalSpacer(height = 12.dp)
        ButtonItemView(title = brand, onClick = onClick)
        VerticalSpacer(height = 8.dp)
        Text(text = description, style = AppTypography.text1)
        VerticalSpacer(height = 8.dp)
    }

    HideOrShowView(
        isViewVisible = isDescriptionVisible,
        onHideOrShowClick = onHideOrShowDescriptionClick
    )
}

@Composable
private fun CharacteristicsView(characteristics: List<Info>) {
    TitleView(text = "Характеристики")
    VerticalSpacer(height = 20.dp)

    characteristics.forEach { info ->
        CharacteristicItem(title = info.title.orEmpty(), value = info.value.orEmpty()) // mappers
    }

}

@Composable
private fun CharacteristicItem(title: String, value: String) {
    Row() {
        Text(text = title, style = AppTypography.text1)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value, style = AppTypography.text1)
    }
    VerticalSpacer(height = 2.dp)
    HorizontalDividerView()
    VerticalSpacer(height = 12.dp)
}

@Composable
private fun IngredientsView(
    description: String,
    isIngredientsExpanded: Boolean,
    isIngredientsTextHasMoreThanTwoLines: Boolean?,
    onHideOrShowIngredientsClick: () -> Unit,
    onIngredientsLinesCountMeasured: (Boolean) -> Unit,
) {
    Row {
        TitleView(text = "Состав")
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.clickable(onClick = {/* No implementation */ }),
            imageVector = Icons.Default.Search,
            contentDescription = ""
        )
    }

    VerticalSpacer(height = 8.dp)

    val maxLines =
        if (isIngredientsTextHasMoreThanTwoLines == null) {
            Int.MAX_VALUE
        } else {
            if (isIngredientsExpanded) {
                Int.MAX_VALUE
            } else {
                2
            }
        }

    Text(
        text = description,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { result ->
            if (isIngredientsTextHasMoreThanTwoLines == null) {
                onIngredientsLinesCountMeasured(result.lineCount > 2)
            }
        },
        style = AppTypography.text1
    )
    VerticalSpacer(height = 8.dp)

    ExpandOrReduceView(
        isViewExpanded = isIngredientsExpanded,
        onHideOrShowClick =
        if (isIngredientsTextHasMoreThanTwoLines == true) {
            { onHideOrShowIngredientsClick.invoke() }
        } else {
            null
        }
    )
}

@Composable
private fun TitleView(text: String) {
    Text(text = text, style = AppTypography.title1)
}

