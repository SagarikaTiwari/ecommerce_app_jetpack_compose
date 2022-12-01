package com.sagarikatiwari.ecommerceapp.data.repositories

import com.sagarikatiwari.ecommerceapp.data.entities.FavoriteProductEntity
import com.sagarikatiwari.ecommerceapp.data.database.dao.WishListDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WishlistDatabaseRepositoryImpl @Inject constructor(
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