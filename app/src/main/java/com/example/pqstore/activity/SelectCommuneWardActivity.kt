package com.example.pqstore.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.R
import com.example.pqstore.adapter.DistrictAdapter
import com.example.pqstore.adapter.ProvinceAdapter
import com.example.pqstore.databinding.ActivitySelectCommuneWardBinding
import com.example.pqstore.databinding.ActivitySelectProvinceBinding
import com.example.pqstore.model.MainViewModel

class SelectCommuneWardActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectCommuneWardBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCommuneWardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val parentId = intent.getStringExtra("parentId")
        binding.btnBack.setOnClickListener() {finish()}
        viewModel.communeWard.observe(this, Observer {
            binding.rvCommuneWard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvCommuneWard.adapter = DistrictAdapter(it) { selectedProvince ->
                val resultIntent = Intent()
                resultIntent.putExtra("address", selectedProvince)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        })
        viewModel.loadCommuneWard(parentId!!)
    }
}