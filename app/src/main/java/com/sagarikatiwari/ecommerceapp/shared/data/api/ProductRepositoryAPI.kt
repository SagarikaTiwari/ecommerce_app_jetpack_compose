package com.sagarikatiwari.ecommerceapp.shared.data.api

import com.sagarikatiwari.ecommerceapp.product_details.business.ProductDetails
import com.sagarikatiwari.ecommerceapp.product_list.business.Product
import com.sagarikatiwari.ecommerceapp.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryAPI @Inject constructor(private val service: ProductService) :
    ProductRepository {

    override suspend fun getProductList(): Resource<List<Product>> {

        val response = try {

            service.getProductList()
        } catch (e: Exception) {

            return Resource.Error("An error occured !")

        }
        return Resource.Success(response.map {
            Product(
                it.title,
                it.description,
                it.price,
                it.imageUrl,
                it.id
            )
        })

    }





    override suspend fun getProductDetails(productId: String): Resource<ProductDetails> {
        val response = try {
            service.getProductDetails(productId)
        } catch (e: Exception) {
            return Resource.Error("An error occured !")

        }

        return Resource.Success(response.run {
            ProductDetails(
                this.title,
                this.description,
                this.full_description,
                "INR $ ${this.price}",
                this.imageUrl,
                this.pros,
                this.cons
            )
        })


    }
}