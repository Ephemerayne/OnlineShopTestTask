package com.nyx.catalog_compose

import androidx.compose.runtime.Composable
import com.nyx.common.views.ScreenTitleView

@Composable
fun CatalogScreen(

) {
    CatalogView()
}

@Composable
private fun CatalogView() {
    ScreenTitleView(text = "Каталог")
}