package com.example.pqstore.adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.CategoryActivity
import com.example.pqstore.ProductDetailsActivity
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderCategoryBinding
import com.example.pqstore.databinding.ViewholderProductBinding
import com.example.pqstore.model.CategoryModel
import com.example.pqstore.model.ProductModel

class CategoryApdapter (val items: MutableList<CategoryModel>) : RecyclerView.Adapter<CategoryApdapter.ViewHolder>() {
    private var context: Context? = null
    inner class ViewHolder(val binding: ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val item = items[position]
            holder.binding.txtName.text = item.name.toString()

            Glide
                .with(holder.binding.root)
                .load(item.imagePath)
                .centerCrop()
                .placeholder(R.drawable.ic_loading_spinner)
                .into(holder.binding.image)

            // click danh mục chuyển đến trang danh mục
            holder.itemView.setOnClickListener() {
                val intent = Intent(holder.itemView.context, CategoryActivity::class.java)
//                val item: ProductModel = items[position]
//                intent.putExtra("object", item)
                holder.itemView.context.startActivity(intent)
            }
        }
    }


}