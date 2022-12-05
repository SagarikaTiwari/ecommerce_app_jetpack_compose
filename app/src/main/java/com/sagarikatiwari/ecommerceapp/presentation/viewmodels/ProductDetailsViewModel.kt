package com.sagarikatiwari.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.domain.usecases.LoadProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val loadProductDetailsUseCase: LoadProductDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _viewState = MutableLiveData<ProductDetailsViewState>()
    val viewState: LiveData<ProductDetailsViewState>
        get() = _viewState

    fun loadProduct(productId: String) {
        viewModelScope.launch(dispatcher) {
            val productDetails = loadProductDetailsUseCase.loadProductDetails(productId)
            when (productDetails) {
                is Resource.Error -> {
                    _viewState.postValue(ProductDetailsViewState.Error)
                }
                is Resource.Loading -> {
                    _viewState.postValue(ProductDetailsViewState.Loading)
                }
                is Resource.Success -> {
                    _viewState.postValue(
                        ProductDetailsViewState.Content(
                            productDetails.data!!
                        )
                    )
                }
            }
        }
    }
}