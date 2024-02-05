package com.nyx.product_card_impl

import com.nyx.common.viewmodel.BaseViewModel
import com.nyx.product_card_impl.models.ProductCardViewAction
import com.nyx.product_card_impl.models.ProductCardViewEvent
import com.nyx.product_card_impl.models.ProductCardViewState

class ProductCardViewModel :
    BaseViewModel<ProductCardViewState, ProductCardViewAction, ProductCardViewEvent>(
        initialState = ProductCardViewState()
    ) {

    override fun obtainEvent(viewEvent: ProductCardViewEvent) {
        when (viewEvent) {
            is ProductCardViewEvent.HideOrShowDescriptionClicked -> toggleDescriptionVisibility()
            is ProductCardViewEvent.HideOrShowIngredientsClicked -> toggleIngredientsVisibility()
            is ProductCardViewEvent.IngredientsTextLinesCountMeasured ->
                setIsIngredientsTextLinesCountMeasured(viewEvent.isLinesCountMoreThanTwo)
            is ProductCardViewEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun toggleDescriptionVisibility() {
        viewState = viewState.copy(isDescriptionVisible = !viewState.isDescriptionVisible)
    }

    private fun toggleIngredientsVisibility() {
        viewState = viewState.copy(isIngredientsExpanded = !viewState.isIngredientsExpanded)
    }

    private fun setIsIngredientsTextLinesCountMeasured(isLinesCountMoreThanTwo: Boolean) {
        viewState = viewState.copy(isIngredientsTextHasMoreThanTwoLines = isLinesCountMoreThanTwo)
    }
}