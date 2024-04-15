package com.example.pqstore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderSizeBinding

@Suppress("DEPRECATION")
class SizeAdapter (val items: MutableList<String>) : RecyclerView.Adapter<SizeAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class ViewHolder(val binding: ViewholderSizeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtSize.text = items[position]

        holder.binding.root.setOnClickListener() {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if (selectedPosition == position) {
            holder.binding.layout.setBackgroundResource(R.drawable.bg_grey_selected)
            holder.binding.txtSize.setTextColor(context.resources.getColor(R.color.my_primary))
        } else {
            holder.binding.layout.setBackgroundResource(R.drawable.bg_grey)
            holder.binding.txtSize.setTextColor(context.resources.getColor(R.color.textDark))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}