package com.example.pqstore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderCartBinding
import com.example.pqstore.helper.ChangeNumberItemsListener
import com.example.pqstore.helper.ManagementCart
import com.example.pqstore.model.ProductModel

class CartAdapter (
    private val listItemSelected: ArrayList<ProductModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener?=null
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val managerCart = ManagementCart(context)

    inner class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItemSelected.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val item = listItemSelected[position]

            holder.binding.txtName.text = item.name.toString()
            holder.binding.txtPrice.text = item.price.toString()
            holder.binding.txtQuantity.text = item.quantity.toString()
            holder.binding.txtTotalEachItem.text = "${Math.round(item.quantity * item.price)}"
            Glide
                .with(holder.binding.root)
                .load(item.images[0])
                .centerCrop()
                .placeholder(R.drawable.ic_loading_spinner)
                .into(holder.binding.image)

//            holder.binding.btnDecrease.setOnClickListener() {
//                managerCart.decrease(listItemSelected, position, object: ChangeNumberItemsListener {
//                    override fun onChange() {
//                        notifyDataSetChanged()
//                        changeNumberItemsListener?.onChange()
//                    }
//                })
//            }
        }
    }


}