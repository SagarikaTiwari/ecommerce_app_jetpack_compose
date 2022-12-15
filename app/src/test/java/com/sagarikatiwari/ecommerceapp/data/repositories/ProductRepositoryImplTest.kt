package com.sagarikatiwari.ecommerceapp.common.data.remote.api

import com.sagarikatiwari.ecommerceapp.data.entities.ProductDetailsEntity
import com.sagarikatiwari.ecommerceapp.data.entities.ProductEntity
import com.sagarikatiwari.ecommerceapp.data.repositories.ProductRepositoryImpl
import com.sagarikatiwari.ecommerceapp.data.remote.ProductService
import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
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
        repository = ProductRepositoryImpl(
            service,
            productEntityToProductDataMapper,
            productDetailsEntityDataMapper
        )
    }

    @Test
    fun `when get product List is  called then it returns entity into Business Objects`() =
        runTest {

            val listOfProductEntity = (0..2).map {
                ProductEntity(
                    "id",
                    "title",
                    "description $it",
                    6.0,
                    ""
                )
            }
            val listOfProducts = (0..2).map {
                Product(
                    "title",
                    "description $it",
                    6.0,
                    "",
                    "id"
                )
            }
            coEvery {
                service.getProductList()
            } returns listOfProductEntity

            coEvery {
                productEntityToProductDataMapper.mapProdcutEntityToProduct(
                    ProductEntity(
                        id = "id",
                        title = "title",
                        description = "description 0",
                        price = 6.0,
                        imageUrl = ""
                    )
                )
            } returns listOfProducts.get(0)
            coEvery {
                productEntityToProductDataMapper.mapProdcutEntityToProduct(
                    ProductEntity(
                        id = "id",
                        title = "title",
                        description = "description 1",
                        price = 6.0,
                        imageUrl = ""
                    )
                )
            } returns listOfProducts.get(1)
            coEvery {
                productEntityToProductDataMapper.mapProdcutEntityToProduct(
                    ProductEntity(
                        id = "id",
                        title = "title",
                        description = "description 2",
                        price = 6.0,
                        imageUrl = ""
                    )
                )
            } returns listOfProducts.get(2)

            val products = repository.getProductList().data!!

            assert(products.size == 3)
            if (products != null) {
                assert(products.get(1).description == "description 1")
            }
        }


    @Test
    fun `when get Product Detail is called then it returns entity into business object`() =
        runTest {
            coEvery {
                service.getProductDetails(any())
            } returns ProductDetailsEntity(
                "title",
                "desc",
                "full_desc",
                10.0,
                "",
                listOf("pros"),
                listOf("cons")
            )

            coEvery {
                productDetailsEntityDataMapper.mapProductDetailsEntityToProductDetails(
                    ProductDetailsEntity(
                        "title",
                        "desc",
                        "full_desc",
                        10.0,
                        "",
                        listOf("pros"),
                        listOf("cons")
                    )
                )
            } returns ProductDetails(
                "title",
                "desc",
                "full_desc",
                "10.0",
                "",
                listOf("pros"),
                listOf("cons")
            )

            assert(repository.getProductDetails("1").data?.title == "title")
            assert(repository.getProductDetails("13").data?.description == "desc")
            assert(repository.getProductDetails("23").data?.price == "10.0")

        }
}