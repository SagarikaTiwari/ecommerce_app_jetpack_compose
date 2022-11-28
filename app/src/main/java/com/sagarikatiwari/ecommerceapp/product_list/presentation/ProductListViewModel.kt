package com.sagarikatiwari.ecommerceapp.product_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarikatiwari.ecommerceapp.repository.CartRepository
import com.sagarikatiwari.ecommerceapp.repository.ProductRepository
import com.sagarikatiwari.ecommerceapp.shared.data.api.Resource
import com.sagarikatiwari.ecommerceapp.shared.presentation.IndianPriceFormatter
import com.sagarikatiwari.ecommerceapp.shared.presentation.PriceFormatter
import com.sagarikatiwari.ecommerceapp.shared.presentation.SingleLiveEvent
import com.sagarikatiwari.ecommerceapp.wishlist.business.AddOrRemoveFromWishListUseCase
import com.sagarikatiwari.ecommerceapp.wishlist.business.IsProductInWishListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val isProductInWishListUseCase: IsProductInWishListUseCase,
    private val addOrRemoveFromWishListUseCase: AddOrRemoveFromWishListUseCase,
    private val cartRepository: CartRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState>
        get() = _viewState

     val cartEvents = SingleLiveEvent<AddToCartEvent>()

    init {
        viewModelScope.launch(dispatcher) {
            cartRepository.cartChanges().collect {
                updateViewStateForCartChanges(it)
            }
        }
    }


    fun loadProductList() {
        viewModelScope.launch(dispatcher) {
            // Data call to fetch products
            val productList = repository.getProductList()
            val productsInCart = cartRepository.cartChanges().first()

            when (productList) {
                is Resource.Loading -> {
                    _viewState.postValue(ProductListViewState.Loading)

                }
                is Resource.Error -> {
                    _viewState.postValue(ProductListViewState.Error)
                }

                is Resource.Success -> {
                    _viewState.postValue(
                        productList.data?.let {
                            ProductListViewState.Content(
                                it.map {
                                    ProductCardViewState(
                                        it.productId,
                                        it.title,
                                        it.description,
                                        IndianPriceFormatter.format(it.price),
                                        it.imageUrl,
                                        isProductInWishListUseCase.execute(it.productId),
                                        productsInCart.contains(it.productId)
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

    private fun updateViewStateForCartChanges(cartItems: List<String>) {
        (_viewState.value as? ProductListViewState.Content)?.let { content ->
            _viewState.postValue(
                ProductListViewState.Content(
                    content.productList.map {
                        it.copy(isProductInCart = cartItems.contains(it.id))
                    }
                )
            )
        }
    }

    fun onBuyClicked(id: String) {
        viewModelScope.launch(dispatcher) {
            if (cartRepository.cartChanges().first().contains(id)) {
                cartEvents.postValue(AddToCartEvent(false))
            } else {
                cartRepository.addToCart(id)
                cartEvents.postValue(AddToCartEvent(true))
            }
        }
    }

    fun removeClicked(id: String) {
        viewModelScope.launch(dispatcher) {
            cartRepository.removeFromCart(id)
        }
    }

}
