package com.example.pqstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.adapter.CartAdapter
import com.example.pqstore.databinding.FragmentCartBinding
import com.example.pqstore.helper.ChangeNumberItemsListener
import com.example.pqstore.helper.ManagementCart
import com.example.pqstore.model.ProductModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        managementCart = ManagementCart(requireContext())
        initCartList()

        return binding.root
    }

    private fun initCartList() {
        binding.rvCart.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCart.adapter = CartAdapter(managementCart.getListCart(), this.requireContext(), object: ChangeNumberItemsListener {
            override fun onChanged() {
                TODO("Not yet implemented")
            }
        })

        with(binding) {
            txtCartEmpty.visibility = if (managementCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView2.visibility = if (managementCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun calculateTotal() {
        val total = Math.round(managementCart.getTotal())

        with(binding) {
            txtTotal.text = total.toString()
        }
    }
}