package com.example.pqstore.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.pqstore.R
import com.example.pqstore.activity.SearchResultActivity
import com.example.pqstore.adapter.CategoryAdapter
import com.example.pqstore.adapter.ProductAdapter
import com.example.pqstore.adapter.SliderAdapter
import com.example.pqstore.databinding.FragmentHomeBinding
import com.example.pqstore.model.ImageModel
import com.example.pqstore.model.MainViewModel
import io.github.muddz.styleabletoast.StyleableToast

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

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Gửi yêu cầu tìm kiếm đến API với từ khóa query
                if (query == null) {
                    StyleableToast.makeText(requireContext(), "Nhập từ khóa để tìm kiếm!", R.style.WarningToast).show()
                } else {
                    val intent = Intent(requireContext(), SearchResultActivity::class.java)
                    intent.putExtra("query", query)
                    startActivity(intent)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        initSliders()
        initCategories()
        initPopular()
        return binding.root
    }

    private fun initSliders() {
        binding.progressBarSlider.visibility = View.VISIBLE
        viewModel.sliders.observe(viewLifecycleOwner, Observer {
            sliders(it)
            binding.progressBarSlider.visibility = View.GONE
        })
        viewModel.loadSliders()
    }
    private fun sliders(images: MutableList<ImageModel>) {
        binding.progressBarSlider.visibility = View.VISIBLE
        binding.viewPagerSlider.adapter = SliderAdapter(images, binding.viewPagerSlider)
        binding.viewPagerSlider.clipToPadding = false
        binding.viewPagerSlider.clipChildren = false
        binding.viewPagerSlider.offscreenPageLimit = 10
        binding.viewPagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        if (images.size > 1) {
            binding.dotIndicatorSlider.visibility = View.VISIBLE
            binding.dotIndicatorSlider.attachTo(binding.viewPagerSlider)
        }
    }

    private fun initCategories() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = CategoryAdapter(it)
            }
            binding.progressBarCategory.visibility = View.GONE
        })
        viewModel.loadCategories()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.populars.observe(viewLifecycleOwner, Observer {
            binding.rvPopular.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            binding.rvPopular.adapter = ProductAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopulars()
    }

}