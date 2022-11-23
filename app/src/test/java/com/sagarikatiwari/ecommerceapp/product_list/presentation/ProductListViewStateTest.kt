package com.sagarikatiwari.ecommerceapp.product_list.presentation

import org.junit.Test

class ProductListViewStateTest {

    @Test
    fun `Correct product view state is updated`(){
        val content = ProductListViewState.Content(
            productList = (0..9).map{
              ProductCardViewState(
                  "$it",
                  "",
                  "",
                  "",
                  "",
                  isFavorite = false,
                  isProductInCart = false
              )
            }
        )
        val result = content.updateFavoriteProduct("4", true)
        assert(!result.productList[3].isFavorite)
        assert(result.productList[4].isFavorite)
        assert(!result.productList[5].isFavorite)
    }
}