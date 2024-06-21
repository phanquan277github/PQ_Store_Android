package com.example.pqstore.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.activity.OrderActivity
import com.example.pqstore.adapter.CartAdapter
import com.example.pqstore.databinding.FragmentCartBinding
import com.example.pqstore.helper.ChangeNumberItemsListener
import com.example.pqstore.helper.Helper
import com.example.pqstore.helper.ManagementCart
import com.example.pqstore.model.CartModel
import com.example.pqstore.model.MainViewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var managementCart: ManagementCart
    private val viewModel = MainViewModel()

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
        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            binding.scrollView2.visibility = if (it.size == 0) View.GONE else View.VISIBLE
            binding.txtCartEmpty.visibility = if (it.size == 0) View.VISIBLE else View.GONE
            binding.constraintLayout4.visibility = if (it.size == 0) View.GONE else View.VISIBLE
            if (it.size != 0) {
                binding.rvCart.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvCart.adapter = CartAdapter(it, this.requireContext(), object: ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateTotal(it)
                    }
                })
                binding.btnOrder.setOnClickListener() {
                    val intent = Intent(requireContext(), OrderActivity::class.java)
                    startActivity(intent)
                }
                calculateTotal(it)
            }
        })
        viewModel.loadCartItems()
    }

    private fun calculateTotal(lists: MutableList<CartModel>) {
        var total: Double = 0.0
        for (i in lists) {
            total += i.price * i.quantity
        }
        binding.txtTotal.text = Helper.formatCurrency(total)
    }
}