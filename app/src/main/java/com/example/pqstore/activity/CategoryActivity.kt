package com.example.pqstore.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pqstore.adapter.ProductAdapter
import com.example.pqstore.databinding.ActivityCategoryBinding
import com.example.pqstore.overlayDialog.FilterOverlayDialog
import com.example.pqstore.model.MainViewModel

class CategoryActivity : AppCompatActivity(), FilterOverlayDialog.FilterListener {
    private lateinit var binding: ActivityCategoryBinding
    private val viewModel = MainViewModel()
    private var id = 0
    private lateinit var fragment: FilterOverlayDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.id = intent.getIntExtra("id", 0)
        binding.txtName.text = intent.getStringExtra("name")
        getBundle()
        binding.btnFilter.setOnClickListener() {
            fragment = FilterOverlayDialog(id)
            fragment.show(supportFragmentManager, "overlay_dialog")
        }
        binding.btnBack.setOnClickListener() {
            finish()
        }
    }

    private fun getBundle() {
        viewModel.catalogProducts.observe(this, Observer {
            binding.rvProducts.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            binding.rvProducts.adapter = ProductAdapter(it)
            binding.progressBar.visibility = View.GONE
        })
        viewModel.loadCatalogProducts(this.id, "id ASC")
    }

    override fun onFilterSelected(sortType: String, shoeType: Int) {
        fragment.dismiss()
        viewModel.catalogProducts.observe(this, Observer {
            binding.rvProducts.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            binding.rvProducts.adapter = ProductAdapter(it)
            binding.progressBar.visibility = View.GONE
        })
        val id = if (shoeType == 0) this.id else shoeType
        viewModel.loadCatalogProducts(id, sortType)
    }
}