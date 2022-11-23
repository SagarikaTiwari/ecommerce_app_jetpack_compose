package com.sagarikatiwari.ecommerceapp.product_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sagarikatiwari.ecommerceapp.cart.business.CartRepository
import com.sagarikatiwari.ecommerceapp.product_list.business.Product
import com.sagarikatiwari.ecommerceapp.shared.business.ProductRepository
import com.sagarikatiwari.ecommerceapp.wishlist.business.AddOrRemoveFromWishListUseCase
import com.sagarikatiwari.ecommerceapp.wishlist.business.IsProductInWishListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
    private val repository = mockk<ProductRepository>()
    private val isProductInWishListUseCase = mockk<IsProductInWishListUseCase>()
    private val cartRepository = mockk<CartRepository>()
    private val addOrRemoveUseCase = mockk<AddOrRemoveFromWishListUseCase>()
    private val listOfProducts = (0..2).map {
        Product("title", "description", 6.0, "", "$it")
    }

    @Before
    fun setUp() {
        // for any id return product in wish list false
        coEvery { isProductInWishListUseCase.execute(any()) } returns false

        // for id =1 return product in wish list true
        coEvery {
            isProductInWishListUseCase.execute("1")
        } returns true

        // return the fake product list
        coEvery {
            repository.getProductList()
        } returns listOfProducts

        viewModel = ProductListViewModel(
            repository,
            isProductInWishListUseCase,
            addOrRemoveUseCase,
            cartRepository,
            dispatcher
        )
    }

    @Test
    fun `Load method correctly creates the ViewState`() = runTest {
        val values = mutableListOf<ProductListViewState>()
        viewModel.viewState.observeForever {
            values.add(it)
        }
        coEvery { cartRepository.observeChanges() } returns flowOf(emptyList())
        viewModel.loadProductList()
        dispatcher.scheduler.advanceUntilIdle() // to make sure that the co routine gets completed before the test goes on

        // checking first value that gets added to the product list view state is loading.
        assert(values[0] is ProductListViewState.Loading)

        // checking the first item of the content list
        assert(
            values[1] ==
                    ProductListViewState.Content(
                        (0..2).map {
                            ProductCardViewState(
                                "$it",
                                "title",
                                "description",
                                "INR 6.0",
                                "",
                                it == 1,
                                false
                            )
                        }
                    ))
    }

    @Test
    fun `Check if ViewState is correct for items in the cart`() = runTest {
        val values = mutableListOf<ProductListViewState>()
        viewModel.viewState.observeForever {
            values.add(it)
        }
        coEvery { cartRepository.observeChanges() } returns flowOf(listOf("2"))
        viewModel.loadProductList()
        dispatcher.scheduler.advanceUntilIdle()
        // to check that loading state is shown in the beginning
        assert(values[0] is ProductListViewState.Loading)

        //
        assert(
            values[1] ==
                    ProductListViewState.Content(
                        (0..2).map {
                            ProductCardViewState(
                                "$it",
                                "title",
                                "description",
                                "INR 6.0",
                                "",
                                it == 1,
                                it == 2
                            )
                        }
                    ))

    }

}