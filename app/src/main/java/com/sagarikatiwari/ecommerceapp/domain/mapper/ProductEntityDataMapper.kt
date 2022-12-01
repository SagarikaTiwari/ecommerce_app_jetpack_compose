package com.sagarikatiwari.ecommerceapp.domain.mapper

import com.sagarikatiwari.ecommerceapp.data.entities.ProductEntity
import com.sagarikatiwari.ecommerceapp.domain.entities.Product

class ProductEntityDataMapper {

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