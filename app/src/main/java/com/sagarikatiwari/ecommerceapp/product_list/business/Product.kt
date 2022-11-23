package com.sagarikatiwari.ecommerceapp.product_list.business

data class Product(
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val productId: String
)