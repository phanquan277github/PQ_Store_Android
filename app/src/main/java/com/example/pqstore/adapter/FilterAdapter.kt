package com.example.pqstore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderSizeBinding
import com.example.pqstore.model.SizeModel

@Suppress("DEPRECATION")
class FilterAdapter(val items: ArrayList<String>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var selectedPositions = HashSet<String>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

    // Setter để gán giá trị cho listener
    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ViewholderSizeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: String = items[position]
        holder.binding.txtSize.text = item

        holder.binding.root.setOnClickListener() {
            if (selectedPositions.contains(item)) {
                selectedPositions.remove(item)
            } else {
                selectedPositions.add(item)
            }
            notifyItemChanged(position)
            listener?.onItemClick(item)
        }

        if (selectedPositions.contains(item)) {
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