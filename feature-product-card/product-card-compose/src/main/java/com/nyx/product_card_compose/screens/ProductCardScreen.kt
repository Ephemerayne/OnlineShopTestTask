package com.nyx.product_card_compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common.models.Info
import com.nyx.common.viewmodel.rememberEvent
import com.nyx.common.views.ButtonItemView
import com.nyx.common.views.CrossedOutPriceView
import com.nyx.common.views.DiscountChipView
import com.nyx.common.views.ExpandOrReduceView
import com.nyx.common.views.HideOrShowView
import com.nyx.common.views.HorizontalDividerView
import com.nyx.common.views.HorizontalSpacer
import com.nyx.common.views.NewPriceView
import com.nyx.common.views.RatingStarsBar
import com.nyx.common.views.VerticalSpacer
import com.nyx.product_card_impl.ProductCardViewModel
import com.nyx.product_card_impl.models.ProductCardViewEvent
import com.nyx.product_card_impl.models.ProductCardViewState

@Composable
fun ProductCardScreen(
    viewModel: ProductCardViewModel = viewModel(),
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val onHideOrShowDescriptionClick =
        viewModel.rememberEvent(ProductCardViewEvent.HideOrShowDescriptionClicked)

    val onHideOrShoIngredientsClick =
        viewModel.rememberEvent(ProductCardViewEvent.HideOrShowIngredientsClicked)

    val test = viewModel.rememberEvent<Boolean, _> {
        ProductCardViewEvent.IngredientsTextLinesCountMeasured(it)
    }

    ProductCardView(
        viewState,
        onHideOrShowDescriptionClick,
        onHideOrShoIngredientsClick,
        test
    )
}

@Composable
private fun ProductCardView(
    viewState: ProductCardViewState,
    onHideOrShowDescriptionClick: () -> Unit,
    onHideOrShowIngredientsClick: () -> Unit,
    onIngredientsLinesCountMeasured: (Boolean) -> Unit,
) {
    Column() {
        HeaderView()
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            item {
                VerticalSpacer(height = 12.dp)
            }
            item {
                ImageView()
            }
            item {
                ProductTitleView()
            }
            item {
                VerticalSpacer(height = 12.dp)
            }
            item {
                AvailableStockView()
            }
            item {
                VerticalSpacer(height = 12.dp)
            }
            item {
                HorizontalDividerView()
            }
            item {
                VerticalSpacer(height = 12.dp)
            }
            item {
                RatingView(rating = 3.5, reviews = 6)
            }
            item {
                VerticalSpacer(height = 16.dp)
            }
            item {
                PriceView(150.0, 300.0, 50)
            }
            item {
                VerticalSpacer(height = 16.dp)
            }
            item {
                DescriptionView(
                    brand = "BRAND",
                    description = "This is decription product This is decription product This is decription product This is decription product This is decription product This is decription productThis is decription product This is decription productThis is decription product",
                    isDescriptionVisible = viewState.isDescriptionVisible,
                    onClick = {/* No implementation */ },
                    onHideOrShowDescriptionClick = onHideOrShowDescriptionClick
                )
            }
            item {
                VerticalSpacer(height = 24.dp)
            }
            item {
                CharacteristicsView(
                    characteristics = listOf(
                        Info("title 1", "value 1"),
                        Info("title 2", "value 2"),
                        Info("title 3", "value 3"),
                        Info("title 4", "value 4")
                    )
                )
            }
            item {
                VerticalSpacer(height = 20.dp)
            }
            item {
                IngredientsView(
                    description = "ct  product ingredients product efefef nts prodgggggggggggggggggg rfffffffffffffffffffffd df fg dfg dfuct  product ingredients produ nts product  product ingredients produ",
                    isIngredientsExpanded = viewState.isIngredientsExpanded,
                    onHideOrShowIngredientsClick = onHideOrShowIngredientsClick,
                    onIngredientsLinesCountMeasured = onIngredientsLinesCountMeasured,
                    isIngredientsTextHasMoreThanTwoLines = viewState.isIngredientsTextHasMoreThanTwoLines
                )
            }
        }
    }
}


// TODO refactoring
@Composable
private fun HeaderView() {
    Row(
        modifier = Modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
                .clickable { },
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

@Composable
private fun ImageView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(368.dp)
                .background(Color.LightGray)
                .align(Alignment.Center)
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
                .padding(4.dp),
            imageVector = Icons.Default.Info,
            contentDescription = ""
        )
    }
}

@Composable
private fun ProductTitleView() {
    Text(text = "BRAND", color = Color.LightGray)
    VerticalSpacer(height = 12.dp)
    Text(
        text = "PRODUCT NAME DESCRIPTION PRODUCT NAME DESCRIPTION PRODUCT NAME DESCRIPTION PRODUCT NAME DESCRIPTION",
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun AvailableStockView() {
    Text(text = "Доступно для заказа 30 штук", color = Color.LightGray)
}

@Composable
private fun RatingView(rating: Double, reviews: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RatingStarsBar(rating = rating)
        HorizontalSpacer(width = 8.dp)
        Text(text = rating.toString(), color = Color.LightGray)
        HorizontalSpacer(width = 4.dp)
        Text(text = "· $reviews отзыва", color = Color.LightGray)
    }
}

@Composable
private fun PriceView(newPrice: Double, oldPrice: Double, discount: Int) {
    Row {
        NewPriceView(price = newPrice)
        HorizontalSpacer(width = 12.dp)
        CrossedOutPriceView(price = oldPrice)
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
    Text(text = "Описание")

    if (isDescriptionVisible) {
        VerticalSpacer(height = 12.dp)
        ButtonItemView(text = brand, onClick = onClick)
        VerticalSpacer(height = 8.dp)
        Text(text = description)
        VerticalSpacer(height = 8.dp)
    }

    HideOrShowView(
        isViewVisible = isDescriptionVisible,
        onHideOrShowClick = onHideOrShowDescriptionClick
    )
}

@Composable
private fun CharacteristicsView(characteristics: List<Info>) {
    Text(text = "Характеристики")
    VerticalSpacer(height = 20.dp)

    characteristics.forEach { info ->
        CharacteristicItem(title = info.title.orEmpty(), value = info.value.orEmpty()) // mappers
    }

}

@Composable
private fun CharacteristicItem(title: String, value: String) {
    Row() {
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value)
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
    Row() {
        Text(text = "Состав")
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.Search, contentDescription = "")
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
        }
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

