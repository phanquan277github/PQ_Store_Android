package com.example.pqstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.pqstore.ProductApdapter
import com.example.pqstore.R
import com.example.pqstore.databinding.FragmentHomeBinding
import com.example.pqstore.model.ProductModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ProductApdapter
    private lateinit var products: ArrayList<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        products = ArrayList<ProductModel>()
        products.add(ProductModel("Nike arri fafawe afhakfb asdfhajfkhasf afhabfabfs afbabfkbajklfkafkb", 100.0, "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg"))
        products.add(ProductModel("Adidas comfort z1 db serial", 10000.00, "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg"))
        products.add(ProductModel("Adidas comfort z1 db serial", 10000.00, "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg"))
        products.add(ProductModel("Adidas comfort z1 db serial", 10000.00, "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg"))
        products.add(ProductModel("Adidas comfort z1 db serial", 10000.00, "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg"))
        products.add(ProductModel("Adidas comfort z1 db serial", 10000.00, "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg"))
        products.add(ProductModel("Adidas comfort z1 db serial", 10000.00, "https://product.hstatic.net/200000099191/product/untitled-11_0dd5cf19bea146338873d19271503c65.jpg"))

        adapter = ProductApdapter(products)
        binding.rvProducts.adapter = adapter
        binding.rvProducts.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        return binding.root
    }
}