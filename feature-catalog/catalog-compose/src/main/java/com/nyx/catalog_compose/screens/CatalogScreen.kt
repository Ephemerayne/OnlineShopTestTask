package com.nyx.catalog_compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ChipDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.catalog_api.navigation.CatalogScreenNavigation
import com.nyx.catalog_compose.R
import com.nyx.catalog_compose.extensions.text
import com.nyx.catalog_compose.navigation.catalogActionNavigation
import com.nyx.catalog_impl.CatalogViewModel
import com.nyx.catalog_impl.models.CatalogViewEvent
import com.nyx.catalog_impl.models.CatalogViewState
import com.nyx.catalog_impl.models.ProductTagType
import com.nyx.catalog_impl.models.SortingType
import com.nyx.common_compose.typography.AppTypography
import com.nyx.common_compose.utils.StableList
import com.nyx.common_compose.utils.toStable
import com.nyx.common_compose.viewmodel.rememberEvent
import com.nyx.common_compose.views.*
import com.nyx.common_compose.R as ColorRes

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
        viewModel.rememberEvent<String, _> { productId ->
            CatalogViewEvent.OnProductClicked(productId)
        }


    val onFavouriteClick =
        viewModel.rememberEvent(CatalogViewEvent.OnFavouriteClicked)

    CatalogView(
        viewState = viewState,
        onExpandSortingClick = onSortingMenuClick,
        onSortingVariantClick = onSortingVariantClick,
        onTagClick = onTagClick,
        onClearTagClick = onClearTagClick,
        onProductClick = onProductClick,
        onFavouriteClick = onFavouriteClick
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
    onProductClick: (productId: String) -> Unit,
    onFavouriteClick: () -> Unit,
) {
    Column(modifier = Modifier) {
        ScreenTitleView(text = stringResource(R.string.catalog_title))
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
        ProductsGridView(
            products = viewState.products,
            onProductClick = onProductClick,
            onFavouriteClick = onFavouriteClick
        )
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
        Image(
            modifier = Modifier.padding(start = 8.dp),
            painter = painterResource(R.drawable.sorting_icon),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = viewState.currentSortingType.text,
            style = AppTypography.title4
        )
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
            ) { Text(text = item.text, style = AppTypography.title4) }
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
        Image(
            modifier = Modifier.padding(start = 8.dp),
            painter = painterResource(R.drawable.filters_icon),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp, end = 8.dp),
            text = stringResource(R.string.filters_title),
            style = AppTypography.title4
        )
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
                colors = ChipDefaults.filterChipColors(
                    selectedBackgroundColor = colorResource(id = ColorRes.color.dark_blue),
                    selectedContentColor = colorResource(id = ColorRes.color.white),
                    contentColor = colorResource(id = ColorRes.color.text_gray),
                    backgroundColor = colorResource(id = ColorRes.color.background_light_gray)
                ),
                onClick = { onTagClick(type) },
                trailingIcon =
                if (isSelected && type != ProductTagType.ALL) {
                    {
                        Image(
                            modifier = Modifier.clickable(onClick = onClearClick),
                            painter = painterResource(R.drawable.close_icon),
                            contentDescription = null
                        )
                    }
                } else null
            ) {
                Text(
                    text = type.text,
                    textAlign = TextAlign.Center,
                    style = if (isSelected) AppTypography.title4 else AppTypography.buttonText2
                )

            }
        }
    }
}


