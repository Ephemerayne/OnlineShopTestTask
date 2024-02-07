package com.nyx.product_card_impl.models

sealed class ProductCardViewEvent {
    object HideOrShowDescriptionClicked : ProductCardViewEvent()
    object HideOrShowIngredientsClicked : ProductCardViewEvent()
    class IngredientsTextLinesCountMeasured(
        val isLinesCountMoreThanTwo: Boolean,
    ) : ProductCardViewEvent()
    class OnFavouriteClicked(val id: String, val isFavourite: Boolean): ProductCardViewEvent()
    object ActionInvoked: ProductCardViewEvent()
}