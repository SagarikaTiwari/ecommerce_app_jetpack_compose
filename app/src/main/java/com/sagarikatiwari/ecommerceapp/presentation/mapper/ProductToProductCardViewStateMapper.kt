package com.sagarikatiwari.ecommerceapp.presentation.mapper

import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductCardViewState
import com.sagarikatiwari.ecommerceapp.domain.usecases.IsProductInWishListUseCase

class ProductToProductCardViewStateMapper(
    private val isProductInWishListUseCase: IsProductInWishListUseCase
) {


    suspend fun mapProductToProductCardView(product: Product): ProductCardViewState {
        return ProductCardViewState(
            product.productId,
            product.title,
            product.description,
            "INR " + product.price.toString(),
            product.imageUrl,
            isProductInWishListUseCase.execute(product.productId),

            )
    }
}