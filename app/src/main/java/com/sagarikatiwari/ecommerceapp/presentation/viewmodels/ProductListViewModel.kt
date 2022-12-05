package com.sagarikatiwari.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductEntityDataMapper
import com.sagarikatiwari.ecommerceapp.domain.usecases.LoadProductListUseCase
import com.sagarikatiwari.ecommerceapp.presentation.mapper.ProductToProductCardViewStateMapper
import com.sagarikatiwari.ecommerceapp.domain.usecases.AddOrRemoveFromWishListUseCase
import com.sagarikatiwari.ecommerceapp.domain.usecases.IsProductInWishListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(

    private val loadProductListUseCase: LoadProductListUseCase,
    private val isProductInWishListUseCase: IsProductInWishListUseCase,
    private val addOrRemoveFromWishListUseCase: AddOrRemoveFromWishListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private var productToProductCardViewStateMapper: ProductToProductCardViewStateMapper,

    ) : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState>
        get() = _viewState

    fun loadProductList() {
        viewModelScope.launch(dispatcher) {
            val productList = loadProductListUseCase.loadProductList()
            when (productList) {
                is Resource.Loading -> {
                    _viewState.postValue(ProductListViewState.Loading)
                }
                is Resource.Error -> {
                    _viewState.postValue(ProductListViewState.Error)
                }
                is Resource.Success -> {
                    productToProductCardViewStateMapper =
                        ProductToProductCardViewStateMapper(isProductInWishListUseCase)
                    _viewState.postValue(
                        productList.data?.let {
                            ProductListViewState.Content(
                                it.map { product ->
                                    productToProductCardViewStateMapper.mapProductToProductCardView(
                                        product
                                    )
                                }
                            )
                        }
                    )
                }
            }
        }
    }

    fun favoriteIconClicked(productId: String) {
        viewModelScope.launch(dispatcher) {
            addOrRemoveFromWishListUseCase.execute(productId)
            val currentViewState = _viewState.value
            (currentViewState as? ProductListViewState.Content)?.let { content ->
                _viewState.postValue(
                    content.updateFavoriteProduct(
                        productId,
                        isProductInWishListUseCase.execute(productId)
                    )
                )
            }
        }
    }
}
