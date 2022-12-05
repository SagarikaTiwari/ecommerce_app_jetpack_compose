package com.sagarikatiwari.ecommerceapp.productdetails.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductDetailsViewModel
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductDetailsViewState
import com.sagarikatiwari.ecommerceapp.domain.entities.ProductDetails
import com.sagarikatiwari.ecommerceapp.domain.usecases.LoadProductDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModelTest {


    private val loadProductDetailsUseCase = mockk<LoadProductDetailsUseCase>()
    private lateinit var viewModel: ProductDetailsViewModel
    private val productDetails =
        ProductDetails(
            "title",
            "description",
            "full description",
            "100.0",
            "",
            listOf("pros"),
            listOf("cons")
        )

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        viewModel = ProductDetailsViewModel(loadProductDetailsUseCase, dispatcher)
    }

    @Test
    fun `When product details are getting fetched then the view stae shows loading`() {
        coEvery { loadProductDetailsUseCase.loadProductDetails("1") } returns Resource.Loading()
        val viewStates = mutableListOf<ProductDetailsViewState>()
        viewModel.viewState.observeForever {
            viewStates.add(it)
        }
        viewModel.loadProduct("1")
        dispatcher.scheduler.advanceUntilIdle()
        assert(viewStates[0] is ProductDetailsViewState.Loading)
    }

    @Test
    fun `When get product details return success then view state is updated correctly`() {
        coEvery { loadProductDetailsUseCase.loadProductDetails("1") } returns Resource.Success(
            productDetails
        )
        val viewStates = mutableListOf<ProductDetailsViewState>()
        viewModel.viewState.observeForever {
            viewStates.add(it)
        }
        viewModel.loadProduct("1")
        dispatcher.scheduler.advanceUntilIdle()
        assert(viewStates[0] is ProductDetailsViewState.Content)
    }

    @Test
    fun `When get product details return error then view state is updated with Error`() {
        coEvery { loadProductDetailsUseCase.loadProductDetails("1") } returns Resource.Error(
            "An error occurred !"
        )
        val viewStates = mutableListOf<ProductDetailsViewState>()
        viewModel.viewState.observeForever {
            viewStates.add(it)
        }
        viewModel.loadProduct("1")
        dispatcher.scheduler.advanceUntilIdle()
        assert(viewStates[0] is ProductDetailsViewState.Error)
    }

}