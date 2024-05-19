package com.example.pqstore.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pqstore.adapter.ProductAdapter
import com.example.pqstore.databinding.ActivityCategoryBinding
import com.example.pqstore.overlayDialog.FilterOverlayDialog
import com.example.pqstore.model.MainViewModel

class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        binding.btnFilter.setOnClickListener() {
            val fragment = FilterOverlayDialog()
            fragment.show(supportFragmentManager, "overlay_dialog")
        }
        binding.btnBack.setOnClickListener() {
            finish()
        }
    }

    private fun getBundle() {
        val query: String = intent.getStringExtra("query").toString()
        viewModel.searchResults.observe(this, Observer {
            binding.rvProducts.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            binding.rvProducts.adapter = ProductAdapter(it)
            binding.progressBar.visibility = View.GONE
        })
        viewModel.loadSearchResults(query)
    }
}