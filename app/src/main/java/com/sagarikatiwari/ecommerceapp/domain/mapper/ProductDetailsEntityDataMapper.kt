package com.sagarikatiwari.ecommerceapp.domain.mapper

import com.sagarikatiwari.ecommerceapp.data.entities.ProductDetailsEntity
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
import javax.inject.Inject

class ProductDetailsEntityDataMapper @Inject constructor(){

    fun mapProductDetailsEntityToProductDetails(productDetailsEntity: ProductDetailsEntity): ProductDetails {

        return ProductDetails(
            productDetailsEntity.title,
            productDetailsEntity.description,
            productDetailsEntity.full_description,
            "INR  ${productDetailsEntity.price}",
            productDetailsEntity.imageUrl,
            productDetailsEntity.pros,
            productDetailsEntity.cons
        )
    }
}