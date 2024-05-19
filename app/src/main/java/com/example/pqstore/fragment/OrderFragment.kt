package com.example.pqstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.adapter.OrderAdapter
import com.example.pqstore.adapter.OrderItemsAdapter
import com.example.pqstore.databinding.FragmentOrderingBinding
import com.example.pqstore.helper.Helper
import com.example.pqstore.model.MainViewModel

class OrderFragment(
    private val orderStatus: String
) : Fragment() {
    private lateinit var binding: FragmentOrderingBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderingBinding.inflate(layoutInflater)

        viewModel.orders.observe(viewLifecycleOwner, Observer {
           if (it.size == 0) {
               binding.txtEmpty.visibility = View.VISIBLE
               binding.rvItems.visibility = View.GONE
           } else {
               binding.txtEmpty.visibility = View.GONE
               binding.rvItems.visibility = View.VISIBLE
               binding.rvItems.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
               binding.rvItems.adapter = OrderAdapter(it)
           }
        })
        viewModel.loadOrders(orderStatus)

        return binding.root
    }
}