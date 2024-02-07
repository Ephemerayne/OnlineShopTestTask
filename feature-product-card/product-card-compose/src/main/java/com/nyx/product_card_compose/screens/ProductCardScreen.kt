package com.nyx.product_card_compose.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common_api.constant.Constants
import com.nyx.common_api.models.ProductEntity
import com.nyx.common_compose.mappers.toUiEntity
import com.nyx.common_compose.models.InfoUiEntity
import com.nyx.common_compose.models.ProductUiEntity
import com.nyx.common_compose.typography.AppTypography
import com.nyx.common_compose.viewmodel.rememberEvent
import com.nyx.common_compose.viewmodel.viewModelFactory
import com.nyx.common_compose.views.*
import com.nyx.product_card_api.navigation.ProductCardScreenNavigation
import com.nyx.product_card_compose.R
import com.nyx.product_card_compose.navigation.productCardActionNavigation
import com.nyx.product_card_impl.ProductCardViewModel
import com.nyx.product_card_impl.models.ProductCardViewEvent
import com.nyx.product_card_impl.models.ProductCardViewState
import com.nyx.common_compose.R as CommonRes

@Composable
fun ProductCardScreen(
    productId: String,
    screenNavigation: ProductCardScreenNavigation,
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    val viewModel: ProductCardViewModel = viewModel(
        factory = viewModelFactory {
            ProductCardViewModel(sharedPreferences = sharedPref, productId = productId)
        })

    val viewState = viewModel.viewStates().observeAsState().value

    val onHideOrShowDescriptionClick =
        viewModel.rememberEvent(ProductCardViewEvent.HideOrShowDescriptionClicked)

    val onHideOrShowIngredientsClick =
        viewModel.rememberEvent(ProductCardViewEvent.HideOrShowIngredientsClicked)

    val onIngredientsLinesCountMeasured = viewModel.rememberEvent<Boolean, _> {
        ProductCardViewEvent.IngredientsTextLinesCountMeasured(it)
    }

    val onFavouriteClick =
        viewModel.rememberEvent<String, Boolean, _> { id, isFavourite ->
            ProductCardViewEvent.OnFavouriteClicked(id, isFavourite)
        }

    ProductCardView(
        viewState = viewState,
        onBackArrowClick = { screenNavigation.back() }, // TODO
        onFavouriteClick = onFavouriteClick,
        onHideOrShowDescriptionClick = onHideOrShowDescriptionClick,
        onExpandOrHideIngredientsClick = onHideOrShowIngredientsClick,
        onIngredientsLinesCountMeasured = onIngredientsLinesCountMeasured
    )

    productCardActionNavigation(viewModel = viewModel, screenNavigation = screenNavigation)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductCardView(
    viewState: ProductCardViewState,
    onBackArrowClick: () -> Unit,
    onFavouriteClick: (productId: String, isFavourite: Boolean) -> Unit,
    onHideOrShowDescriptionClick: () -> Unit,
    onExpandOrHideIngredientsClick: () -> Unit,
    onIngredientsLinesCountMeasured: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(0)
    val product = viewState.product ?: ProductEntity().toUiEntity()

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            HeaderView(
                onBackArrowClick = onBackArrowClick,
                onShareIconClick = { /* No implementation */ })
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                VerticalSpacer(height = 12.dp)
                ImagePagerView(
                    product = product,
                    pagerState = pagerState,
                    imagesCount = 4,
                    onFavouriteClick = onFavouriteClick
                )
                VerticalSpacer(height = 8.dp)
                ImagePagerIndicatorView(
                    pagerState = pagerState,
                    imagesCount = 4,
                    indicator = IndicatorSettings(
                        selectedColor = colorResource(id = CommonRes.color.pink),
                        unselectedColor = colorResource(id = CommonRes.color.element_light_gray)
                    ),
                )
                VerticalSpacer(height = 8.dp)
                ProductTitleView(
                    brand = product.title,
                    title = product.subtitle
                )
                VerticalSpacer(height = 12.dp)
                AvailableStockView(availableStock = product.available)
                VerticalSpacer(height = 12.dp)
                HorizontalDividerView(thickness = 0.5.dp)
                VerticalSpacer(height = 12.dp)
                RatingView(
                    rating = product.feedback.rating,
                    reviews = product.feedback.count
                )
                VerticalSpacer(height = 16.dp)
                PriceView(
                    newPrice = product.price.priceWithDiscount,
                    oldPrice = product.price.price,
                    discount = product.price.discount,
                    unit = product.price.unit
                )
                VerticalSpacer(height = 16.dp)
                DescriptionView(
                    brand = product.title,
                    description = product.subtitle,
                    isDescriptionVisible = viewState.isDescriptionVisible,
                    onClick = {/* No implementation */ },
                    onHideOrShowDescriptionClick = onHideOrShowDescriptionClick
                )
                VerticalSpacer(height = 24.dp)
                CharacteristicsView(
                    characteristics = product.info
                )
                VerticalSpacer(height = 20.dp)
                IngredientsView(
                    description = product.ingredients,
                    isIngredientsExpanded = viewState.isIngredientsExpanded,
                    onHideOrShowIngredientsClick = onExpandOrHideIngredientsClick,
                    onIngredientsLinesCountMeasured = onIngredientsLinesCountMeasured,
                    isIngredientsTextHasMoreThanTwoLines = viewState.isIngredientsTextHasMoreThanTwoLines
                )
                VerticalSpacer(height = 80.dp)
            }
        }
        AddToCartButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            newPrice = product.price.priceWithDiscount,
            oldPrice = product.price.price,
            unit = product.price.unit,
            onClick = {/* No implementation */ }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImagePagerView(
    product: ProductUiEntity,
    pagerState: PagerState,
    imagesCount: Int,
    onFavouriteClick: (productId: String, isFavourite: Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ImagePager(
            pagerState = pagerState,
            imagesCount = imagesCount
        )
        // TODO box
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .clickable(onClick = { onFavouriteClick(product.id, product.isFavourite) }),
            painter = painterResource(if (product.isFavourite) CommonRes.drawable.selected_favourite_icon else CommonRes.drawable.favourite_icon),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 4.dp, bottom = 16.dp),
            painter = painterResource(R.drawable.image_info_icon),
            contentDescription = null
        )
    }
}

@Composable
private fun ProductTitleView(brand: String, title: String) {
    Text(
        text = brand,
        color = colorResource(id = CommonRes.color.text_gray),
        style = AppTypography.title1
    )
    VerticalSpacer(height = 12.dp)
    Text(
        text = title,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        style = AppTypography.largeTitle,
    )
}

@Composable
private fun AvailableStockView(availableStock: Int) {
    Text(
        text = pluralStringResource(R.plurals.available_stock, availableStock, availableStock),
        color = colorResource(id = CommonRes.color.text_gray),
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
            color = colorResource(id = CommonRes.color.text_gray),
            style = AppTypography.text1
        )
        HorizontalSpacer(width = 4.dp)
        Text(
            text = pluralStringResource(R.plurals.review_count, reviews, reviews),
            color = colorResource(id = CommonRes.color.text_gray),
            style = AppTypography.text1
        )
    }
}

@Composable
private fun PriceView(
    newPrice: String,
    oldPrice: String,
    discount: Int,
    unit: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        NewPriceView(
            price = newPrice,
            unit = unit,
            textStyle = AppTypography.priceText
        )
        HorizontalSpacer(width = 12.dp)
        CrossedOutPriceView(price = oldPrice, unit = unit, textStyle = AppTypography.text1)
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
    TitleView(text = stringResource(R.string.description_title))

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
private fun CharacteristicsView(characteristics: List<InfoUiEntity>) {
    TitleView(text = stringResource(R.string.characteristics_title))
    VerticalSpacer(height = 20.dp)

    characteristics.forEach { info ->
        CharacteristicItem(title = info.title, value = info.value)
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
        TitleView(text = stringResource(R.string.ingredients_title))
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.clickable(onClick = {/* No implementation */ }),
            painter = painterResource(id = R.drawable.copy_icon),
            contentDescription = null
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

