package com.example.pqstore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderColorBinding

class ColorAdapter (val items: ArrayList<String>) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class ViewHolder(val binding: ViewholderColorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ViewholderColorBinding.inflate(view, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ColorAdapter.ViewHolder, position: Int) {
        val item = items[position]

        Glide
            .with(holder.binding.root)
            .load(items[position])
            .centerCrop()
            .placeholder(R.drawable.ic_loading_spinner)
            .into(holder.binding.image)

        holder.binding.root.setOnClickListener() {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if (selectedPosition == position) {
            holder.binding.layoutColor.setBackgroundResource(R.drawable.bg_grey_selected)
        } else {
            holder.binding.layoutColor.setBackgroundResource(R.drawable.bg_grey)
        }
    }

}