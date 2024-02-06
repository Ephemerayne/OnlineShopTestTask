package com.nyx.favourites_compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nyx.favourites_compose.R
import com.nyx.favourites_impl.models.TabType

val TabType.text: String
    @Composable get() = when (this) {
        TabType.PRODUCT -> stringResource(id = R.string.tab_products_text)
        TabType.BRANDS -> stringResource(id = R.string.tab_brands_text)
    }
