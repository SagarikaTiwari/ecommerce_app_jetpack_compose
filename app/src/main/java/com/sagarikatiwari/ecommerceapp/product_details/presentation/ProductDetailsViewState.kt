package com.sagarikatiwari.ecommerceapp.product_details.presentation

import com.sagarikatiwari.ecommerceapp.product_details.business.ProductDetails

sealed class ProductDetailsViewState {
    object Loading : ProductDetailsViewState()
    data class Content(val product: ProductDetails) : ProductDetailsViewState()
    object Error : ProductDetailsViewState()
}