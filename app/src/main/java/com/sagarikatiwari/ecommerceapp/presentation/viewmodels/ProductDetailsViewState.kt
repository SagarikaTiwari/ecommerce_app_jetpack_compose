package com.sagarikatiwari.ecommerceapp.presentation.viewmodels

import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails

sealed class ProductDetailsViewState {
    object Loading : ProductDetailsViewState()
    data class Content(val product: ProductDetails) : ProductDetailsViewState()
    object Error : ProductDetailsViewState()
}