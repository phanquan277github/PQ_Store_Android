package com.example.pqstore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderCartBinding
import com.example.pqstore.databinding.ViewholderListOrderBinding
import com.example.pqstore.helper.ChangeNumberItemsListener
import com.example.pqstore.helper.Helper
import com.example.pqstore.helper.ManagementCart
import com.example.pqstore.model.CartModel

class OrderItemsAdapter(
    private val lists: MutableList<CartModel>,
) : RecyclerView.Adapter<OrderItemsAdapter.ViewHolder>() {
    private var context: Context? = null

    inner class ViewHolder(val binding: ViewholderListOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderListOrderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lists[position]
        holder.itemView.apply {
            holder.binding.txtName.text = item.name
            holder.binding.txtPrice.text = Helper.formatCurrency(item.price)
            holder.binding.txtSize.text = item.size
            holder.binding.txtQuantity.text = "x" + item.quantity.toString()
            Glide
                .with(holder.binding.root)
                .load(item.imagePath)
                .centerCrop()
                .placeholder(R.drawable.ic_loading_spinner)
                .into(holder.binding.image)
        }
    }


}