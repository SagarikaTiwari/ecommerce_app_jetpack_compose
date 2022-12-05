package com.sagarikatiwari.ecommerceapp.domain.mapper

import com.sagarikatiwari.ecommerceapp.data.entities.ProductEntity
import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import javax.inject.Inject

class ProductEntityDataMapper @Inject constructor() {

    fun mapProdcutEntityToProduct(
        productEntity: ProductEntity
    ): Product {
        return (
                Product(
                    productEntity.title,
                    productEntity.description,
                    productEntity.price,
                    productEntity.imageUrl,
                    productEntity.id
                ))
    }
}