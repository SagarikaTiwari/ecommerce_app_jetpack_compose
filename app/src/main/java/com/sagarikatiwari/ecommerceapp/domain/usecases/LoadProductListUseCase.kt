package com.sagarikatiwari.ecommerceapp.domain.usecases

import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.data.repositories.ProductRepository
import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import javax.inject.Inject

class LoadProductListUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend fun loadProductList(): Resource<List<Product>> {
        return productRepository.getProductList()
    }
}