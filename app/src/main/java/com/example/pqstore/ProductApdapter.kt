package com.example.pqstore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pqstore.databinding.LayoutProductItemBinding
import com.example.pqstore.model.ProductModel

class ProductApdapter (val list: ArrayList<ProductModel>) : RecyclerView.Adapter<ProductApdapter.ViewHolder>() {
    private lateinit var binding: LayoutProductItemBinding
//    private lateinit var onItemDeleteClickListener: OnItemDeleteClickListener
//    private lateinit var onItemContentClickListener: OnItemContentClickListener

    inner class ViewHolder(view: LayoutProductItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        binding = LayoutProductItemBinding.inflate(view, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            binding.txtName.text = list[position].name.toString()
            binding.txtPrice.text = list[position].price.toString()
            Glide
                .with(binding.root)
                .load(list[position].imagePath.toString())
                .centerCrop()
                .placeholder(R.drawable.ic_loading_spinner)
                .into(binding.image)

//            binding.image. = list[position].foodPrice.toString()
//            binding.txtFoodQuantity.text = list[position].foodQuantity.toString()

//            binding.btnDelete.setOnClickListener {
//                if (onItemDeleteClickListener != null) {
//                    onItemDeleteClickListener!!.onItemDeleteClick(position, list[position])
//                }
//            }
//
//            binding.itemContent.setOnClickListener {
//                if (onItemContentClickListener != null) {
//                    onItemContentClickListener!!.onItemContentClick(position, list[position])
//                }
//            }
        }
    }

    fun setData(newData: ArrayList<ProductModel>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

//    fun setOnItemContentClickListener(onClickListener: OnItemContentClickListener) {
//        this.onItemContentClickListener = onClickListener
//    }
//
//    fun setOnItemClickListener(onClickListener: OnItemDeleteClickListener) {
//        this.onItemDeleteClickListener = onClickListener
//    }
//
//    interface OnItemContentClickListener {
//        fun onItemContentClick(position: Int, model: FoodModel)
//    }
//
//    interface OnItemDeleteClickListener {
//        fun onItemDeleteClick(position: Int, model: FoodModel)
//    }

}