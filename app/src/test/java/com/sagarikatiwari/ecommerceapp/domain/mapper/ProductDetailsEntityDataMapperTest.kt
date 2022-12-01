package com.sagarikatiwari.ecommerceapp.domain.mapper

import com.sagarikatiwari.ecommerceapp.data.entities.ProductDetailsEntity
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
import org.junit.Test

internal class ProductDetailsEntityDataMapperTest {

    private val productDetailsEntity = ProductDetailsEntity(
        "title",
        "description",
        "full description",
        100.0,
        "",
        listOf("pros"),
        listOf("cons")
    )

    private lateinit var productDetailsEntityDataMapper: ProductDetailsEntityDataMapper

    @Test
    fun `when mapper function is called then it maps productDetailsEntity to productDetails`() {
        productDetailsEntityDataMapper = ProductDetailsEntityDataMapper()

        val productDetails = productDetailsEntityDataMapper.mapProductDetailsEntityToProductDetails(
            productDetailsEntity
        )

        val productDetailsExcpected = ProductDetails(
            title = "title",
            description = "description",
            fullDescription = "full description",
            price = "INR  100.0",
            imageUrl = "",
            pros = listOf("pros"),
            cons = listOf("cons")
        )

        assert(
            productDetails == productDetailsExcpected
        )
    }
}