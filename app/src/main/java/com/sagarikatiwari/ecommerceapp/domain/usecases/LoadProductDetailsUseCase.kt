package com.sagarikatiwari.ecommerceapp.domain.usecases

import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.data.repositories.ProductRepository
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
import javax.inject.Inject

class LoadProductDetailsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend fun loadProductDetails(productId: String): Resource<ProductDetails> {
        return productRepository.getProductDetails(productId)
    }
}