package com.sagarikatiwari.ecommerceapp.wishlist.data.repository

import com.sagarikatiwari.ecommerceapp.data.entities.FavoriteProductEntity
import com.sagarikatiwari.ecommerceapp.data.database.dao.WishListDAO
import com.sagarikatiwari.ecommerceapp.data.repositories.WishlistDatabaseRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WishlistDatabaseRepositoryImplTest {
    private lateinit var repository: WishlistDatabaseRepositoryImpl
    private val dao = mockk<WishListDAO>()

    @Before
    fun setup() {
        repository = WishlistDatabaseRepositoryImpl(dao)
    }

    @Test
    fun `When given product is saved in Database, then repository returns true`() = runTest {
        coEvery {
            dao.isProductFavorite("1")
        } returns FavoriteProductEntity("1", "name")
        assert(repository.isFavorite("1"))
    }

    @Test
    fun `When given product is not saved in Database, then repository returns false`() = runTest {
        coEvery {
            dao.isProductFavorite("1")
        } returns null
        assert(!repository.isFavorite("1"))
    }
}