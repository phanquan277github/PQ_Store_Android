package com.example.pqstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pqstore.adapter.ProductAdapter
import com.example.pqstore.databinding.FragmentFavoriteBinding
import com.example.pqstore.model.MainViewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        initFavoriteList()
        return binding.root
    }

    private fun initFavoriteList() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            binding.rvFavorite.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            binding.rvFavorite.adapter = ProductAdapter(it)
            binding.progressBar.visibility = View.GONE
        })
        viewModel.loadFavorite()
    }

}