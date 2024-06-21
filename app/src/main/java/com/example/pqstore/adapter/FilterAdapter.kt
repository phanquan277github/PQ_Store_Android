package com.example.pqstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.R
import com.example.pqstore.activity.CategoryActivity
import com.example.pqstore.databinding.ViewholderCategoryBinding
import com.example.pqstore.databinding.ViewholderSizeBinding
import com.example.pqstore.model.CategoryModel
import com.example.pqstore.model.SizeModel

@Suppress("DEPRECATION")
class FilterAdapter(val items: MutableList<CategoryModel>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var selectedPosition = -1
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(selectedItems: Int)
    }

    // Setter để gán giá trị cho listener
    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.txtName.text = item.name
        Glide
            .with(holder.binding.root)
            .load(item.imagePath)
            .centerCrop()
            .placeholder(R.drawable.ic_loading_spinner)
            .into(holder.binding.image)


        holder.binding.root.setOnClickListener() {
            val previousSelectedPosition = selectedPosition
            selectedPosition = if (selectedPosition == position) -1 else position

            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
            listener?.onItemClick(items[selectedPosition].id)
        }

        if (selectedPosition == position) {
            holder.binding.txtName.setTextColor(context.resources.getColor(R.color.my_primary))
        } else {
            holder.binding.txtName.setTextColor(context.resources.getColor(R.color.textDark))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}