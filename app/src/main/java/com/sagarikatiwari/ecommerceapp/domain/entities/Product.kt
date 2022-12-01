package com.sagarikatiwari.ecommerceapp.domain.entities

data class Product(
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val productId: String
)