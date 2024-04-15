package com.example.pqstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.ProductDetailsActivity
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderProductBinding
import com.example.pqstore.model.ProductModel

class ProductApdapter (val items: MutableList<ProductModel>) : RecyclerView.Adapter<ProductApdapter.ViewHolder>() {
    private var context: Context? = null
    inner class ViewHolder(val binding: ViewholderProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderProductBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            holder.binding.txtName.text = items[position].name.toString()
            holder.binding.txtPrice.text = items[position].price.toString()
            Glide
                .with(holder.binding.root)
                .load(items[position].images[0])
                .centerCrop()
                .placeholder(R.drawable.ic_loading_spinner)
                .into(holder.binding.image)
            holder.itemView.setOnClickListener() {
                val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
                intent.putExtra("product", items[position])
                holder.itemView.context.startActivity(intent)
            }
        }
    }


}