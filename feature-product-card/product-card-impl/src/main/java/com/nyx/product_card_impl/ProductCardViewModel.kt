package com.nyx.product_card_impl

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.nyx.common_api.repository.product.ProductRepository
import com.nyx.common_compose.mappers.toUiEntity
import com.nyx.common_compose.viewmodel.BaseViewModel
import com.nyx.product_card_impl.models.ProductCardViewAction
import com.nyx.product_card_impl.models.ProductCardViewEvent
import com.nyx.product_card_impl.models.ProductCardViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductCardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
) :
    BaseViewModel<ProductCardViewState, ProductCardViewAction, ProductCardViewEvent>(
        initialState = ProductCardViewState()
    ) {

    private val productId: String? = savedStateHandle["productId"]

    init {
        fetchProduct()
    }

    override fun obtainEvent(viewEvent: ProductCardViewEvent) {
        when (viewEvent) {
            is ProductCardViewEvent.HideOrShowDescriptionClicked -> toggleDescriptionVisibility()
            is ProductCardViewEvent.HideOrShowIngredientsClicked -> toggleIngredientsVisibility()
            is ProductCardViewEvent.IngredientsTextLinesCountMeasured ->
                setIsIngredientsTextLinesCountMeasured(viewEvent.isLinesCountMoreThanTwo)

            is ProductCardViewEvent.OnFavouriteClicked -> toggleProductToFavourites(
                viewEvent.id,
                viewEvent.isFavourite
            )

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
        if (viewState.product == null) return

        viewState =
            viewState.copy(isIngredientsTextHasMoreThanTwoLines = isLinesCountMoreThanTwo)
    }

    private fun fetchProduct() {
        if (productId == null) return

        viewModelScope.launch {
            productRepository.getProduct(productId).collect { product ->
                if (product != null) {
                    viewState = viewState.copy(product = product.toUiEntity())
                }
            }
        }
    }

    private fun toggleProductToFavourites(productId: String, isFavourite: Boolean) {
        if (isFavourite) {
            productRepository.deleteFavourite(productId)
        } else {
            productRepository.addFavourite(productId)
        }
    }
}