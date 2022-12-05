package com.sagarikatiwari.ecommerceapp.domain.repositories

import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductDetailsEntityDataMapper
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductEntityDataMapper


interface ProductRepository {

    suspend fun getProductList(): Resource<List<Product>>

    suspend fun getProductDetails(productId: String): Resource<ProductDetails>
}