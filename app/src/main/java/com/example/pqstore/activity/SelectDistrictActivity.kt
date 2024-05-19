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
import com.example.pqstore.databinding.ActivitySelectDistrictBinding
import com.example.pqstore.model.MainViewModel

class SelectDistrictActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectDistrictBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectDistrictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val parentId = intent.getStringExtra("parentId")
        binding.btnBack.setOnClickListener() {finish()}
        viewModel.districts.observe(this, Observer {
            binding.rvDistrict.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvDistrict.adapter = ProvinceAdapter(it) { selectedProvince ->
                val intent = Intent(this, SelectCommuneWardActivity::class.java)
                intent.putExtra("parentId", selectedProvince)
                startActivityForResult(intent, 3)
            }
        })
        viewModel.loadDistricts(parentId!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            val address = data?.getStringExtra("address")
            val resultIntent = Intent()
            resultIntent.putExtra("address", address)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

}