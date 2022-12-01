package com.sagarikatiwari.ecommerceapp.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sagarikatiwari.ecommerceapp.databinding.ProductDetailsFragmentBinding
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductDetailsViewModel
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductDetailsViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var binding: ProductDetailsFragmentBinding
    private val viewModel: ProductDetailsViewModel by viewModels()
    private val arguments: ProductDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadProduct(
            arguments.stringProductId
        )
        viewModel.viewState.observe(viewLifecycleOwner) {
            updateUi(it)
        }
    }

    private fun updateUi(viewState: ProductDetailsViewState) {
        when (viewState) {
            is ProductDetailsViewState.Content -> {
                with(binding) {
                    binding.loadingView.isVisible = false
                    binding.errorView.isVisible = false

                    val product = viewState.product
                    viewProductTitle.text = product.title
                    Glide.with(requireContext()).load(product.imageUrl)
                        .into(viewProductImage)
                    binding.viewPrice.text = product.price
                    binding.viewFullDescription.text = product.fullDescription
                }
            }
            ProductDetailsViewState.Error -> {
                binding.errorView.isVisible = true

                binding.loadingView.isVisible = false
            }
            ProductDetailsViewState.Loading -> {
                binding.errorView.isVisible = false

                binding.loadingView.isVisible = true
            }
        }
    }
}