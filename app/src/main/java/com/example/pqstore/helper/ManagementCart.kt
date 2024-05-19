package com.example.pqstore.helper

import android.content.Context
import com.example.pqstore.model.CartModel
import com.example.pqstore.model.MainViewModel

class ManagementCart(val context: Context?) {
    private val viewModel = MainViewModel()

    fun minusItem(lists: MutableList<CartModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (lists[position].quantity == 1) {
            lists[position].quantity--
            lists.removeAt(position)
        } else {
            lists[position].quantity--
        }
//        tinyDB.putListObject("CartList", lists)
        listener.onChanged()
    }

    fun plusItem(listFood: MutableList<CartModel>, position: Int, listener: ChangeNumberItemsListener) {
        listFood[position].quantity++
//        tinyDB.putListObject("CartList", listFood)
        listener.onChanged()
    }

    fun getTotal(lists: MutableList<CartModel>): Double {
        var fee = 0.0
        for (item in lists) {
            fee += item.price * item.quantity
        }
        return fee
    }
}