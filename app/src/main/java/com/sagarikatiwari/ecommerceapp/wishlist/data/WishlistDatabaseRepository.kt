package com.sagarikatiwari.ecommerceapp.wishlist.data

import com.sagarikatiwari.ecommerceapp.repository.WishlistRepository
import com.sagarikatiwari.ecommerceapp.wishlist.data.database.FavoriteProductEntity
import com.sagarikatiwari.ecommerceapp.wishlist.data.database.WishListDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WishlistDatabaseRepository @Inject constructor(
    private val databaseDAO: WishListDAO
) : WishlistRepository {

    override suspend fun isFavorite(productId: String): Boolean {
        return withContext(Dispatchers.IO) {
            databaseDAO.isProductFavorite(productId) != null
        }
    }

    override suspend fun addToWishlist(productId: String) {
        return withContext(Dispatchers.IO) {
            databaseDAO.addProductToFavorites(
                FavoriteProductEntity(productId, "")
            )
        }
    }

    override suspend fun removeFromWishlist(productId: String) {
        return withContext(Dispatchers.IO) {
            databaseDAO.removeProductFromFavorites(
                FavoriteProductEntity(productId, "")
            )
        }
    }
}