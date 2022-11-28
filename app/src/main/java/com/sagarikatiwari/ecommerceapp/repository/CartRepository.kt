package com.sagarikatiwari.ecommerceapp.repository

import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun cartChanges(): Flow<List<String>>
    suspend fun addToCart(productId: String)
    suspend fun removeFromCart(id: String)
}