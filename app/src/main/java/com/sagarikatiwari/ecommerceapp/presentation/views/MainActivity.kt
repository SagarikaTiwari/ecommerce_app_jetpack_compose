package com.sagarikatiwari.ecommerceapp.presentation.views

import android.os.Bundle
import android.view.Menu
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sagarikatiwari.ecommerceapp.R
import com.sagarikatiwari.ecommerceapp.databinding.ActivityMainBinding
import com.sagarikatiwari.ecommerceapp.presentation.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    private var productCountTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.viewState.observe(this) {
            productCountTextView?.isVisible = it.isBadgeVisible
            productCountTextView?.text = "${it.productsInCartCount}"
            val animator = AnimationUtils.loadAnimation(this, R.anim.textview_animator)
            productCountTextView?.startAnimation(animator)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val actionView = menu?.getItem(0)?.actionView
        productCountTextView = actionView?.findViewById(R.id.viewCounter)
        return true
    }

    fun onGroupItemClick(item: android.view.MenuItem) {
        // TODO Implement logic here to show a cart screen
    }
}