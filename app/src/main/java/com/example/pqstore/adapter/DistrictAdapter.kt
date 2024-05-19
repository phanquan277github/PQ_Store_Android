package com.example.pqstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.pqstore.activity.ProductDetailsActivity
import com.example.pqstore.activity.SelectCommuneWardActivity
import com.example.pqstore.activity.SelectDistrictActivity
import com.example.pqstore.databinding.ViewholderSelectAddressBinding
import com.example.pqstore.model.AddressModel

class DistrictAdapter (
    private val items: MutableList<AddressModel>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {
    private var context: Context? = null
    inner class ViewHolder(val binding: ViewholderSelectAddressBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderSelectAddressBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {
            holder.binding.txtName.text = item.nameWithType
            holder.itemView.setOnClickListener { onItemClick(item.pathWithType) }
        }

    }
}