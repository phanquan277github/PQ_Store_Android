package com.example.pqstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.activity.CategoryActivity
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderCategoryBinding
import com.example.pqstore.databinding.ViewholderOrderBinding
import com.example.pqstore.fragment.OrderFragment
import com.example.pqstore.helper.Helper
import com.example.pqstore.model.OrderModel

class OrderAdapter(val items: MutableList<OrderModel>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var context: Context? = null
    inner class ViewHolder(val binding: ViewholderOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderOrderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {
            holder.binding.txtName.text = item.name
            holder.binding.txtPhone.text = item.phone
            holder.binding.txtAddress.text = item.address
            holder.binding.txtTotal.text = Helper.formatCurrency(item.total)
            holder.binding.txtPaymentMethods.text = if (item.paymentMethod == "CashOnDelivery") "Thanh toán khi nhận hàng" else "Thẻ tín dụng/Ghi nợ"
            holder.binding.txtOrderDate.text = item.orderDate

            holder.binding.rvItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.binding.rvItems.adapter = OrderItemsAdapter(item.orderItems)
        }
    }


}