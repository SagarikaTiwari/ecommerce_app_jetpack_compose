package com.sagarikatiwari.ecommerceapp.repository

import com.sagarikatiwari.ecommerceapp.product_list.business.Product
import com.sagarikatiwari.ecommerceapp.product_details.business.ProductDetails
import com.sagarikatiwari.ecommerceapp.shared.data.api.Resource


interface ProductRepository {

    suspend fun getProductList(): Resource<List<Product>>

    suspend fun getProductDetails(productId: String): Resource<ProductDetails>
}