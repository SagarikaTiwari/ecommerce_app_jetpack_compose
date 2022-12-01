package com.sagarikatiwari.ecommerceapp.presentation.adapter

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.sagarikatiwari.ecommerceapp.R
import com.sagarikatiwari.ecommerceapp.databinding.ProductCardBinding
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.ProductCardViewState

class ProductListViewHolder(
    itemView: View,
    val onItemClicked: (ProductCardViewState) -> Unit,
    val onFavoriteIconClicked: (ProductCardViewState) -> Unit,
    val onBuyCLicked: (ProductCardViewState) -> Unit,
    val onRemoveClicked: (ProductCardViewState) -> Unit,
) : RecyclerView.ViewHolder(itemView) {
    fun bind(productCardViewState: ProductCardViewState) {
        val bind = ProductCardBinding.bind(itemView)
        itemView.setOnClickListener {
            onItemClicked(productCardViewState)
        }
        bind.apply {
            viewProductName.text = productCardViewState.title
            viewProductDescription.text = productCardViewState.description
            productPrice.text = productCardViewState.price

            viewWishlistIcon.setOnClickListener {
                onFavoriteIconClicked.invoke(
                    productCardViewState
                )
            }
            buyButton.setOnClickListener {
                onBuyCLicked.invoke(productCardViewState)
            }
            removeButton.setOnClickListener {
                onRemoveClicked.invoke(productCardViewState)
            }
            buyButton.isInvisible = false
            removeButton.isInvisible = true
            viewWishlistIcon.setImageDrawable(
                if (productCardViewState.isFavorite) {
                    ResourcesCompat.getDrawable(
                        viewWishlistIcon.resources,
                        R.drawable.ic_baseline_favorite,
                        null
                    )
                } else {
                    ResourcesCompat.getDrawable(
                        viewWishlistIcon.resources,
                        R.drawable.ic_baseline_favorite_disabled,
                        null
                    )
                }
            )
            Glide.with(productImage)
                .asBitmap()
                .load(productCardViewState.imageUrl)
                .into(BitmapImageViewTarget(productImage))
        }
    }


}