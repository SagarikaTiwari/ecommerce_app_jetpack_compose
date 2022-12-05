package com.sagarikatiwari.ecommerceapp.productlist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sagarikatiwari.ecommerceapp.domain.entities.Product
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductListViewModel
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductListViewState
import com.sagarikatiwari.ecommerceapp.data.remote.Resource
import com.sagarikatiwari.ecommerceapp.domain.mapper.ProductEntityDataMapper
import com.sagarikatiwari.ecommerceapp.domain.usecases.AddOrRemoveFromWishListUseCase
import com.sagarikatiwari.ecommerceapp.domain.usecases.IsProductInWishListUseCase
import com.sagarikatiwari.ecommerceapp.domain.usecases.LoadProductListUseCase
import com.sagarikatiwari.ecommerceapp.presentation.mapper.ProductToProductCardViewStateMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ProductListViewModel
    private val loadProductListUseCase = mockk<LoadProductListUseCase>()
    private val isProductInWishListUseCase = mockk<IsProductInWishListUseCase>()
    private val addOrRemoveUseCase = mockk<AddOrRemoveFromWishListUseCase>()
    private val productToProductCardViewStateMapper = mockk<ProductToProductCardViewStateMapper>()
    private val listOfProduct = (0..2).map {
        Product("title", "description", 6.0, "", "$it")
    }
    private lateinit var result: Resource.Success<List<Product>>

    @Before
    fun setUp() {
         coEvery {
            isProductInWishListUseCase.execute(any())
        } returns false
         coEvery {
            isProductInWishListUseCase.execute("1")
        } returns true
        viewModel = ProductListViewModel(
            loadProductListUseCase,
            isProductInWishListUseCase,
            addOrRemoveUseCase,
            dispatcher,
            productToProductCardViewStateMapper
        )
    }

    @Test
    fun `When productList is loading then show Loading state`() = runTest {
        coEvery {
            loadProductListUseCase.loadProductList()
        } returns Resource.Loading()

        val viewStates = mutableListOf<ProductListViewState>()
        viewModel.viewState.observeForever {
            viewStates.add(it)
        }

        viewModel.loadProductList()
        dispatcher.scheduler.advanceUntilIdle()

        assert(viewStates[0] is ProductListViewState.Loading)
    }

    @Test
    fun `When productList loads returns corrects then shows Success`() = runTest {
        coEvery {
            loadProductListUseCase.loadProductList()
        } returns Resource.Success(listOfProduct)

        val viewStates = mutableListOf<ProductListViewState>()
        viewModel.viewState.observeForever {
            viewStates.add(it)
        }

        viewModel.loadProductList()
        dispatcher.scheduler.advanceUntilIdle()

        assert(viewStates[0] is ProductListViewState.Content)
    }

    @Test
    fun `When loadProductList resturns error then ViewState updates to Error`() = runTest {
        coEvery {
            loadProductListUseCase.loadProductList()
        } returns Resource.Error("An error occurred !")

        val viewStates = mutableListOf<ProductListViewState>()
        viewModel.viewState.observeForever {
            viewStates.add(it)
        }

        viewModel.loadProductList()
        dispatcher.scheduler.advanceUntilIdle()

        assert(viewStates[0] is ProductListViewState.Error)
    }

}