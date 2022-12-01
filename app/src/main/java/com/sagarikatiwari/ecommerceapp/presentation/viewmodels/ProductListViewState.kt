package com.sagarikatiwari.ecommerceapp.presentation.viewmodels

import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductListViewState.Content

sealed class ProductListViewState {
    object Loading : ProductListViewState()
    object Error : ProductListViewState()
    data class Content(val productList: List<ProductCardViewState>) : ProductListViewState()
}

fun Content.updateFavoriteProduct(
    productId: String,
    isFavorite: Boolean
): Content {
    return Content(productList = this.productList.map { viewState ->
        if (viewState.id == productId) {
            viewState.copy(isFavorite = isFavorite)
        } else {
            viewState
        }
    }
    )
}