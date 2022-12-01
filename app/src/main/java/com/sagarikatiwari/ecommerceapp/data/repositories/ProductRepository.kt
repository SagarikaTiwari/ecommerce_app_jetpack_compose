package com.sagarikatiwari.ecommerceapp.data.repositories

import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
import com.sagarikatiwari.ecommerceapp.data.remote.Resource


interface ProductRepository {

    suspend fun getProductList(): Resource<List<Product>>

    suspend fun getProductDetails(productId: String): Resource<ProductDetails>
}