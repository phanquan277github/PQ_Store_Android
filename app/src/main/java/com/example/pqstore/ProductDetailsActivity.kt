package com.example.pqstore

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pqstore.adapter.ColorAdapter
import com.example.pqstore.adapter.SizeAdapter
import com.example.pqstore.adapter.SliderApdapter
import com.example.pqstore.databinding.ActivityProductDetailsBinding
import com.example.pqstore.model.ProductModel
import com.example.pqstore.model.SliderModel

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var item: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        banners()
        initLists()
    }
    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.txtName.text = item.name
        binding.txtPrice.text = item.price.toString()
        binding.txtRate.text = item.rating.toString()
        binding.txtDescriptions.text = item.description

        binding.btnBack.setOnClickListener() {finish()}
    }
    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (item in item.images) {
            sliderItems.add(SliderModel(item))
        }

        binding.sliderImages.adapter = SliderApdapter(sliderItems, binding.sliderImages)
        binding.sliderImages.clipChildren = false
        binding.sliderImages.clipToPadding = false
        binding.sliderImages.offscreenPageLimit = 3
        binding.sliderImages.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        if (sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.sliderImages)
        }
    }
    private fun initLists() {
        val sizes = ArrayList<String>()
        for (item in item.sizes) {
            sizes.add(item.toString())
        }
        binding.rvSize.adapter = SizeAdapter(sizes)
        binding.rvSize.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colors = ArrayList<String>()
        for (item in item.colors) {
            colors.add(item.toString())
        }
        binding.rvColor.adapter = ColorAdapter(colors)
        binding.rvColor.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}