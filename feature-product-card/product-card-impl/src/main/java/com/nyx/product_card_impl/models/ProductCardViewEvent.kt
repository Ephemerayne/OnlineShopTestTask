package com.nyx.product_card_impl.models

sealed class ProductCardViewEvent {
    object HideOrShowDescriptionClicked : ProductCardViewEvent()
    object HideOrShowIngredientsClicked : ProductCardViewEvent()
    class IngredientsTextLinesCountMeasured(
        val isLinesCountMoreThanTwo: Boolean,
    ) : ProductCardViewEvent()
}