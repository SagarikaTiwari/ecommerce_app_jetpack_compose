package com.sagarikatiwari.ecommerceapp.product_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarikatiwari.ecommerceapp.repository.ProductRepository
import com.sagarikatiwari.ecommerceapp.shared.data.api.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _viewState = MutableLiveData<ProductDetailsViewState>()
    val viewState: LiveData<ProductDetailsViewState>
        get() = _viewState

    fun loadProduct(productId: String) {
        viewModelScope.launch(dispatcher) {


            val productDetails = repository.getProductDetails(productId)

            when (productDetails) {
                is Resource.Error -> {
                    _viewState.postValue(ProductDetailsViewState.Error)
                }
                is Resource.Loading -> {
                    _viewState.postValue(ProductDetailsViewState.Loading)
                }
                is Resource.Success -> {
                    _viewState.postValue(ProductDetailsViewState.Content(productDetails.data!!))

                }
            }
        }
    }
}