package com.sagarikatiwari.ecommerceapp.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sagarikatiwari.ecommerceapp.databinding.ProductListFragmentBinding
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductCardViewState
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductListViewModel
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductListViewState
import com.sagarikatiwari.ecommerceapp.presentation.adapter.ProductCardListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private lateinit var binding: ProductListFragmentBinding
    private val viewModel: ProductListViewModel by viewModels()
    private val adapter =
        ProductCardListAdapter(::onItemClicked, ::onFavoriteIconClicked, ::onBuyItCLicked, ::onRemoveClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductRecyclerView()

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            updateUI(viewState)
        }

        viewModel.loadProductList()
    }

    private fun updateUI(viewState: ProductListViewState) {
        when (viewState) {
            is ProductListViewState.Content -> {
                binding.viewProductList.isVisible = true
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = false
                adapter.setData(viewState.productList)
            }
            ProductListViewState.Error -> {
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = true
                binding.loadingView.isVisible = false
            }
            ProductListViewState.Loading -> {
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = true
            }
        }
    }

    // parameter just to show how to retrieve data from Adapter to the fragment
    private fun onItemClicked(viewState: ProductCardViewState) {


        findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment().setStringProductId(viewState.id))
     }

    private fun onBuyItCLicked(viewState: ProductCardViewState) {
        viewModel.onBuyClicked(viewState.id)
    }
    private fun onRemoveClicked(viewState: ProductCardViewState){
        viewModel.removeClicked(viewState.id)
    }

    private fun onFavoriteIconClicked(viewState: ProductCardViewState) {
        viewModel.favoriteIconClicked(viewState.id)
    }

    private fun setupProductRecyclerView() {
        binding.viewProductList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.viewProductList.adapter = adapter
    }
}