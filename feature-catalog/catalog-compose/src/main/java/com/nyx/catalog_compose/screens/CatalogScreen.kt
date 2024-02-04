package com.nyx.catalog_compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.catalog_api.navigation.CatalogScreenNavigation
import com.nyx.catalog_compose.navigation.catalogActionNavigation
import com.nyx.catalog_impl.CatalogViewModel
import com.nyx.catalog_impl.models.CatalogViewEvent
import com.nyx.catalog_impl.models.CatalogViewState
import com.nyx.catalog_impl.models.ProductTagType
import com.nyx.catalog_impl.models.SortingType
import com.nyx.common.utils.StableList
import com.nyx.common.utils.toStable
import com.nyx.common.viewmodel.rememberEvent
import com.nyx.common.views.CrossedOutPriceView
import com.nyx.common.views.DiscountChipView
import com.nyx.common.views.HorizontalSpacer
import com.nyx.common.views.NewPriceView
import com.nyx.common.views.ScreenTitleView
import com.nyx.common.views.VerticalSpacer

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel = viewModel(),
    screenNavigation: CatalogScreenNavigation,
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val onSortingMenuClick =
        viewModel.rememberEvent(CatalogViewEvent.OnSortingDropDownMenuClicked)

    val onSortingVariantClick =
        viewModel.rememberEvent<SortingType, _> { type ->
            CatalogViewEvent.OnSortingVariantClicked(type)
        }

    val onTagClick = viewModel.rememberEvent<ProductTagType, _> { type ->
        CatalogViewEvent.OnTagClicked(type)
    }

    val onClearTagClick =
        viewModel.rememberEvent(CatalogViewEvent.OnClearTagClicked)

    val onProductClick =
        viewModel.rememberEvent(CatalogViewEvent.OnProductClicked)



    CatalogView(
        viewState = viewState,
        onExpandSortingClick = onSortingMenuClick,
        onSortingVariantClick = onSortingVariantClick,
        onTagClick = onTagClick,
        onClearTagClick = onClearTagClick,
        onProductClick = onProductClick
    )

    catalogActionNavigation(viewModel = viewModel, navigation = screenNavigation)
}

@Composable
private fun CatalogView(
    viewState: CatalogViewState,
    onExpandSortingClick: () -> Unit,
    onSortingVariantClick: (SortingType) -> Unit,
    onTagClick: (ProductTagType) -> Unit,
    onClearTagClick: () -> Unit,
    onProductClick: () -> Unit,
) {
    Column(modifier = Modifier) {
        ScreenTitleView(text = "Каталог")
        VerticalSpacer(height = 12.dp)
        Row {
            SortingView(
                viewState = viewState,
                expandMenuClick = onExpandSortingClick,
                onSortingVariantClick = onSortingVariantClick
            )
            Spacer(modifier = Modifier.weight(1f))
            FiltersView(onClick = {/* No implementation */ })
        }
        TagsCarouselView(
            currentTagType = viewState.currentTag,
            availableTags = viewState.availableTags.toStable(),
            onTagClick = onTagClick,
            onClearClick = onClearTagClick
        )
        ProductsView(onProductClick = onProductClick)
    }
}

@Composable
private fun SortingView(
    viewState: CatalogViewState,
    expandMenuClick: () -> Unit,
    onSortingVariantClick: (SortingType) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .clickable(onClick = expandMenuClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.padding(start = 8.dp),
            imageVector = Icons.Default.Info,
            contentDescription = null
        )
        Text(modifier = Modifier.padding(start = 8.dp), text = viewState.currentSortingType.text)
        Icon(
            modifier = Modifier.padding(start = 4.dp),
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null
        )
    }

    DropdownMenu(
        expanded = viewState.isSortingMenuExpanded,
        onDismissRequest = expandMenuClick
    ) {
        SortingType.values().forEach { item ->
            DropdownMenuItem(
                onClick = { onSortingVariantClick(item) }
            ) { Text(text = item.text) }
        }
    }
}

@Composable
private fun FiltersView(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            modifier = Modifier.padding(start = 8.dp),
            imageVector = Icons.Default.Settings,
            contentDescription = null
        )
        Text(modifier = Modifier.padding(start = 4.dp, end = 8.dp), text = "Фильтры")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TagsCarouselView(
    currentTagType: ProductTagType,
    availableTags: StableList<ProductTagType>,
    onTagClick: (ProductTagType) -> Unit,
    onClearClick: () -> Unit,
) {
    LazyRow(modifier = Modifier.padding(start = 8.dp)) {
        items(availableTags) { type ->
            val isSelected = currentTagType == type

            FilterChip(
                modifier = Modifier.padding(4.dp),
                selected = isSelected,
                onClick = { onTagClick(type) },
                trailingIcon =
                if (isSelected && type != ProductTagType.ALL) {
                    {
                        Icon(
                            modifier = Modifier.clickable(onClick = onClearClick),
                            imageVector = Icons.Default.Clear,
                            contentDescription = ""
                        )
                    }
                } else null

            ) {
                Text(text = type.text, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
private fun ProductsView(onProductClick: () -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 8.dp),
        content = {
            items(9) { product ->
                ProductItem(onProductClick)
            }
        })
}

@Composable
private fun ProductItem(onProductClick: () -> Unit) {
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
                .background(Color.Blue)
                .fillMaxWidth()
                .height(144.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = ""
            )
        }
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


