package com.nyx.product_card_impl.models

import com.nyx.common_compose.models.ProductUiEntity

data class ProductCardViewState(
    val isDescriptionVisible: Boolean = true,
    val isIngredientsExpanded: Boolean = false,
    val isIngredientsTextHasMoreThanTwoLines: Boolean? = null,
    val product: ProductUiEntity? = null
)