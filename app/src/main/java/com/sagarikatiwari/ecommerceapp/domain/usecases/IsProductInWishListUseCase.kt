package com.sagarikatiwari.ecommerceapp.domain.usecases

import com.sagarikatiwari.ecommerceapp.data.repositories.WishlistRepository
import javax.inject.Inject

class IsProductInWishListUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend fun execute(productId: String): Boolean =
        wishlistRepository.isFavorite(productId)
}