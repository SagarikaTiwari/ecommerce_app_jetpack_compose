package com.sagarikatiwari.ecommerceapp.data.remote

import com.sagarikatiwari.ecommerceapp.data.entities.ProductDetailsEntity
import com.sagarikatiwari.ecommerceapp.data.entities.ProductEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    suspend fun getProductList(): List<ProductEntity>

    @GET("productDetails")
    suspend fun getProductDetails(@Query("productId") productId: String): ProductDetailsEntity
}