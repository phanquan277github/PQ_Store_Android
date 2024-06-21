package com.example.pqstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.activity.CategoryActivity
import com.example.pqstore.R
import com.example.pqstore.activity.ProductDetailsActivity
import com.example.pqstore.databinding.ViewholderCategoryBinding
import com.example.pqstore.model.CategoryModel

class CategoryAdapter(val items: MutableList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
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
        val item = items[position]
        holder.itemView.apply {
            holder.binding.txtName.text = item.name
            Glide
                .with(holder.binding.root)
                .load(item.imagePath)
                .centerCrop()
                .placeholder(R.drawable.ic_loading_spinner)
                .into(holder.binding.image)

            // click danh mục chuyển đến trang danh mục
            holder.itemView.setOnClickListener() {
                val intent = Intent(holder.itemView.context, CategoryActivity::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("name", item.name)
                holder.itemView.context.startActivity(intent)
            }
        }
    }


}