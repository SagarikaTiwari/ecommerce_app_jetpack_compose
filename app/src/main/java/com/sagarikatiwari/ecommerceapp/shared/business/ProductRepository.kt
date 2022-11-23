package com.sagarikatiwari.ecommerceapp.shared.business

import com.sagarikatiwari.ecommerceapp.product_list.business.Product
import com.sagarikatiwari.ecommerceapp.product_details.business.ProductDetails


interface ProductRepository {

    suspend fun getProductList(): List<Product>

    suspend fun getProductDetails(productId: String): ProductDetails
}