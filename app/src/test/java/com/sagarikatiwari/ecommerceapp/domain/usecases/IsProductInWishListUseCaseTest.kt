package com.sagarikatiwari.ecommerceapp.domain.usecases

import com.sagarikatiwari.ecommerceapp.data.repositories.WishlistRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class IsProductInWishListUseCaseTest {
    private val wishlistRepository = mockk<WishlistRepository>()
    private lateinit var useCase: IsProductInWishListUseCase


    @Before
    fun setUp() {
        useCase = IsProductInWishListUseCase(wishlistRepository)
        coEvery {
            useCase.execute(any())
        } returns false
        coEvery { useCase.execute("100") } returns true
        coEvery { useCase.execute("123") } returns true



    }

    @Test
    fun `when product is in wishlist then isFavourite function returns true `() = runTest {

        assert(useCase.execute("100"))

        assert(useCase.execute("123"))


    }

    @Test
    fun `when product is not in wishlist then isFavourite function returns false `() = runTest {

        assert(!useCase.execute("1"))

        assert(!useCase.execute("13"))


    }

}