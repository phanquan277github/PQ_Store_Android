package com.example.pqstore.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.R
import com.example.pqstore.adapter.ProductAdapter
import com.example.pqstore.adapter.UserAddressAdapter
import com.example.pqstore.databinding.ActivityUserAddressBinding
import com.example.pqstore.model.MainViewModel
import com.example.pqstore.model.UserAddressModel

class UserAddressActivity : AppCompatActivity(), UserAddressAdapter.OnAddressClickListener {
    private lateinit var binding: ActivityUserAddressBinding
    private var viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.address.observe(this, Observer {
            binding.rvAddress.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvAddress.adapter = UserAddressAdapter(it, this)
        })
        viewModel.loadUserAddress()

        binding.btnAddAddress.setOnClickListener() {
            val intent = Intent(this, AddAddressActivity::class.java)
            intent.putExtra("type", "createAddress")
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener() {finish()}
    }

    override fun onAddressClick(id: Int, address: String) {
//        val result = Bundle().apply {
//            putString("selected_address", address)
//        }
//        setFragmentResult("address_request_key", result)
//        dismiss()
    }
}