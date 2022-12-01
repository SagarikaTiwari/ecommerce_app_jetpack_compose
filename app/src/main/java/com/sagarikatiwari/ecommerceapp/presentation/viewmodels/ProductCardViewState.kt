package com.sagarikatiwari.ecommerceapp.presentation.viewmodels

data class ProductCardViewState(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val isFavorite: Boolean,
)