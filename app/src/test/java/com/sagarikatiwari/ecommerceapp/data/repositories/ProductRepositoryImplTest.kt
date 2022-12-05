package com.sagarikatiwari.ecommerceapp.common.data.remote.api

import com.sagarikatiwari.ecommerceapp.data.entities.ProductEntity
import com.sagarikatiwari.ecommerceapp.data.repositories.ProductRepositoryImpl
import com.sagarikatiwari.ecommerceapp.data.remote.ProductService
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductDetailsEntityDataMapper
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductEntityDataMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductRepositoryImplTest {

    private lateinit var repository: ProductRepositoryImpl
    private val service = mockk<ProductService>()
    private val productEntityToProductDataMapper = mockk<ProductEntityDataMapper>()
    private val productDetailsEntityDataMapper = mockk<ProductDetailsEntityDataMapper>()

    @Before
    fun setup() {
        repository = ProductRepositoryImpl(service, productEntityToProductDataMapper, productDetailsEntityDataMapper)
    }

    @Test
    fun `when get product List is  called then it returns entity into Business Objects`() = runTest {
        coEvery {
            service.getProductList()
        } returns (0..2).map {
            ProductEntity(
                "id",
                "title",
                "description $it",
                6.0,
                ""
            )
        }
        val products = repository.getProductList().data!!

        assert(products.size == 3)
        assert(products[1].description == "description 1")
    }


}
