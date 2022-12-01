package com.sagarikatiwari.ecommerceapp.data.repositories

import com.sagarikatiwari.ecommerceapp.data.remote.ProductService
import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductDetailsEntityDataMapper
import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductEntityDataMapper
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val service: ProductService) :
    ProductRepository {

    override suspend fun getProductList(): Resource<List<Product>> {

        val response = try {

            service.getProductList()
        } catch (e: Exception) {

            return Resource.Error("An error occured !")

        }
        var productEntityToProductDataMapper: ProductEntityDataMapper = ProductEntityDataMapper()
        return Resource.Success(response.map {
            productEntityToProductDataMapper.mapProdcutEntityToProduct(it)
        })

    }


    override suspend fun getProductDetails(productId: String): Resource<ProductDetails> {
        val response = try {
            service.getProductDetails(productId)
        } catch (e: Exception) {
            return Resource.Error("An error occured !")

        }

        var productDetailsEntityDataMapper: ProductDetailsEntityDataMapper =
            ProductDetailsEntityDataMapper()

        return Resource.Success(response.run {
            productDetailsEntityDataMapper.mapProductDetailsEntityToProductDetails(this)
        })


    }
}