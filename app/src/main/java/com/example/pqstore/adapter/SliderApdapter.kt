package com.example.pqstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.pqstore.ProductDetailsActivity
import com.example.pqstore.R
import com.example.pqstore.databinding.ViewholderProductBinding
import com.example.pqstore.model.ProductModel
import com.example.pqstore.model.SliderModel

class SliderApdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderApdapter.ViewHolder>() {
    private var context: Context? = null
    private val runnable = Runnable {
        sliderItems = sliderItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imageView: ImageView = itemView.findViewById(R.id.imageSlide)

        fun setImage(sliderItems: SliderModel, context: Context) {
            Glide
                .with(context)
                .load(sliderItems.url)
                .centerCrop()
                .placeholder(R.drawable.ic_loading_spinner)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.slider_item_container, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = sliderItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            holder.setImage(sliderItems[position], context)
            if (position == sliderItems.lastIndex-1)
                viewPager2.post(runnable)
        }
    }


}