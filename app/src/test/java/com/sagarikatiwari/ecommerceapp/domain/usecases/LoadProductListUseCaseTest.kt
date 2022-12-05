package com.sagarikatiwari.ecommerceapp.domain.usecases

import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.domain.repositories.ProductRepository
import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductEntityDataMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LoadProductListUseCaseTest {

    private val productRepository = mockk<ProductRepository>(relaxed = true)
    private val listOfProducts = (0..2).map {
        Product(
            "title",
            "description",
            100.0,
            "",
            "$it"
        )
    }
    private lateinit var useCase: LoadProductListUseCase

    @Before
    fun setUp() {
        useCase = LoadProductListUseCase(productRepository)
    }

    @Test
    fun `When getProductList is called then List gets populated correctly`() = runTest {

        coEvery {
            productRepository.getProductList()
        } returns Resource.Success(listOfProducts)

        useCase.loadProductList()

        coVerify { productRepository.getProductList() }
    }

}