package com.sagarikatiwari.ecommerceapp.productlist.presentation

import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductCardViewState
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductListViewState
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.updateFavoriteProduct
import org.junit.Test

class ProductListViewStateTest {

    @Test
    fun `When id matches product Id then Correct product view state is updated`(){
        val content = ProductListViewState.Content(
            productList = (0..9).map{
              ProductCardViewState(
                  "$it",
                  "",
                  "",
                  "",
                  "",
                  isFavorite = false,
               )
            }
        )
        val result = content.updateFavoriteProduct("4", true)
        assert(!result.productList[3].isFavorite)
        assert(result.productList[4].isFavorite)
        assert(!result.productList[5].isFavorite)
    }
}