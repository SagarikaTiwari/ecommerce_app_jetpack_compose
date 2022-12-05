package com.sagarikatiwari.ecommerceapp.domain.usecases

import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.domain.repositories.ProductRepository
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductDetailsEntityDataMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LoadProductDetailsUseCaseTest {

    private val productRepository = mockk<ProductRepository>(relaxed = true)
    private val productDetails =
        ProductDetails(
            "title",
            "description",
            "full description",
            "100.0",
            "",
            listOf("pros"),
            listOf("cons")
        )
    private lateinit var useCase: LoadProductDetailsUseCase

    @Before
    fun setUp() {
        useCase = LoadProductDetailsUseCase(productRepository)
    }

    @Test
    fun `When getProductDetails is called then Details gets populated correctly`() = runTest {
        coEvery {
            productRepository.getProductDetails("any Id")
        } returns Resource.Success(productDetails)
        useCase.loadProductDetails("any Id")
        coVerify { productRepository.getProductDetails("any Id")}
    }

}