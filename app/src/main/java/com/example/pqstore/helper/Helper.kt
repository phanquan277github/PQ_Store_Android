package com.example.pqstore.helper

import java.text.NumberFormat
import java.util.Locale

class Helper {
    companion object {
        fun formatCurrency(amount: Double): String {
            val format = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
            return format.format(amount)
        }
    }
}