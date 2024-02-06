package com.nyx.product_card_impl.models

data class ProductCardViewState(
    val isDescriptionVisible: Boolean = true,
    val isIngredientsExpanded: Boolean = false,
    val isIngredientsTextHasMoreThanTwoLines: Boolean? = null,
)