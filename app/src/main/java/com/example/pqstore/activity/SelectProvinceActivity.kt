package com.example.pqstore.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.R
import com.example.pqstore.adapter.ProvinceAdapter
import com.example.pqstore.databinding.ActivitySelectProvinceBinding
import com.example.pqstore.model.MainViewModel

class SelectProvinceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectProvinceBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectProvinceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener() {finish()}
        viewModel.provinces.observe(this, Observer {
            binding.rvProvinces.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvProvinces.adapter = ProvinceAdapter(it) { selectedProvince ->
                val intent = Intent(this, SelectDistrictActivity::class.java)
                intent.putExtra("parentId", selectedProvince)
                startActivityForResult(intent, 2)
            }
        })
        viewModel.loadProvinces()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val address = data?.getStringExtra("address")
            val resultIntent = Intent()
            resultIntent.putExtra("address", address)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

}