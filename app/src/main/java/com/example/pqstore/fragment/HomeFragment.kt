package com.example.pqstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pqstore.adapter.PopularApdapter
import com.example.pqstore.adapter.ProductApdapter
import com.example.pqstore.databinding.FragmentHomeBinding
import com.example.pqstore.model.ProductModel
import com.example.pqstore.viewModel.MainViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        initPopular()
        return binding.root
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.populars.observe(viewLifecycleOwner, Observer {
            binding.rvPopular.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            binding.rvPopular.adapter = PopularApdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }

}