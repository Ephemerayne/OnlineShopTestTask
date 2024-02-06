package com.nyx.catalog_compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nyx.catalog_compose.R
import com.nyx.catalog_impl.models.ProductTagType
import com.nyx.catalog_impl.models.SortingType

val SortingType.text: String
    @Composable get() = when (this) {
        SortingType.BY_POPULAR -> stringResource(id = R.string.filter_type_by_popular)
        SortingType.BY_DECREASE_PRICE -> stringResource(id = R.string.filter_type_by_decrease_price)
        SortingType.BY_INCREASE_PRICE -> stringResource(id = R.string.filter_type_by_increase_price)
    }

val ProductTagType.text: String
    @Composable get() = when (this) {
        ProductTagType.ALL -> stringResource(id = R.string.tag_type_all)
        ProductTagType.FACE -> stringResource(id = R.string.tag_type_face)
        ProductTagType.BODY -> stringResource(id = R.string.tag_type_body)
        ProductTagType.SUNTAN -> stringResource(id = R.string.tag_type_suntan)
        ProductTagType.MASKS -> stringResource(id = R.string.tag_type_masks)
    }