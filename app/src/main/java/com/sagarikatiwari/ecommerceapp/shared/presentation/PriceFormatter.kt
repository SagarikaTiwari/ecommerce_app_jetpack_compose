package com.sagarikatiwari.ecommerceapp.shared.presentation

import android.content.Context
import com.sagarikatiwari.ecommerceapp.R


interface PriceFormatter {
    fun format(value : Double) : String
}

object IndianPriceFormatter : PriceFormatter {

    override fun format(value: Double): String {
        return "\\u20B9 ${value}"
    }

}