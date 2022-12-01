package com.sagarikatiwari.ecommerceapp.domain.mapper

import com.sagarikatiwari.ecommerceapp.data.entities.ProductEntity
import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import org.junit.Test

internal class ProductEntityDataMapperTest {

    private lateinit var productDetailsEntityDataMapper: ProductEntityDataMapper
    private val productEntity = ProductEntity(
        "1",
        "title",
        "desc",
        100.0,
        ""
    )

    @Test
    fun `when mapper function is called then it maps ProductEntity to Product correctly`() {


        productDetailsEntityDataMapper = ProductEntityDataMapper()
        val productEntity = productDetailsEntityDataMapper.mapProdcutEntityToProduct(productEntity)

        val productEntityExpected = Product(
            "title",
            "desc",
            100.0,
            "",
            "1"
        )

        assert(productEntity == productEntityExpected)

    }

}