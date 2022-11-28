package com.sagarikatiwari.ecommerceapp.shared.data.api

import com.sagarikatiwari.ecommerceapp.product_details.data.ProductDetailsEntity
import com.sagarikatiwari.ecommerceapp.product_list.data.ProductEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    suspend fun getProductList(): List<ProductEntity>

    @GET("productDetails")
    suspend fun getProductDetails(@Query("productId") productId: String): ProductDetailsEntity
}