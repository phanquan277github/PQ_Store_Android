package com.example.pqstore.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.R
import com.example.pqstore.adapter.OrderItemsAdapter
import com.example.pqstore.databinding.ActivityUserOrdersBinding
import com.example.pqstore.fragment.HomeFragment
import com.example.pqstore.fragment.OrderFragment
import com.example.pqstore.helper.Helper
import com.example.pqstore.model.MainViewModel

class UserOrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(OrderFragment("ordering"))
        binding.btnOrdering.setTextColor(getColor(R.color.my_primary))

        binding.btnBack.setOnClickListener() {finish()}

        binding.btnOrdering.setOnClickListener() {
            binding.btnOrdering.setTextColor(getColor(R.color.my_primary))
            binding.btnDelivering.setTextColor(getColor(R.color.textDark))
            binding.btnDelivered.setTextColor(getColor(R.color.textDark))
            replaceFragment(OrderFragment("ordering"))
        }
        binding.btnDelivering.setOnClickListener() {
            binding.btnOrdering.setTextColor(getColor(R.color.textDark))
            binding.btnDelivering.setTextColor(getColor(R.color.my_primary))
            binding.btnDelivered.setTextColor(getColor(R.color.textDark))
            replaceFragment(OrderFragment("delivering"))
        }
        binding.btnDelivered.setOnClickListener() {
            binding.btnOrdering.setTextColor(getColor(R.color.textDark))
            binding.btnDelivering.setTextColor(getColor(R.color.textDark))
            binding.btnDelivered.setTextColor(getColor(R.color.my_primary))
            replaceFragment(OrderFragment("delivered"))
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameLayout.id, fragment)
        fragmentTransaction.commit()
    }
}