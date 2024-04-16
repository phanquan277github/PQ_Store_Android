package com.example.pqstore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pqstore.adapter.ProductApdapter
import com.example.pqstore.databinding.ActivityCategoryBinding
import com.example.pqstore.viewModel.MainViewModel

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProducts()

        binding.btnFilter.setOnClickListener() {
            val fragment = OverlayDialogFragment()
            fragment.show(supportFragmentManager, "overlay_dialog")
        }

    }

    private fun initProducts() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.populars.observe(this, Observer {
            binding.rvProducts.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            binding.rvProducts.adapter = ProductApdapter(it)
            binding.progressBar.visibility = View.GONE
        })
        viewModel.loadPopular()
    }
}