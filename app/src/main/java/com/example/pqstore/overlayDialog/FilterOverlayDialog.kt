package com.example.pqstore.overlayDialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBindings
import com.example.pqstore.adapter.CategoryAdapter
import com.example.pqstore.adapter.FilterAdapter
import com.example.pqstore.adapter.ProductAdapter
import com.example.pqstore.databinding.DialogFilterBinding
import com.example.pqstore.model.MainViewModel

class FilterOverlayDialog (cateId: Int) : DialogFragment() {
    private val cateId: Int = cateId
    private lateinit var binding: DialogFilterBinding
    private var listener: FilterListener? = null
    private val viewModel = MainViewModel()
    private var shoeType = 0
    private var sortType = "id ASC"

    interface FilterListener {
        fun onFilterSelected(sortType: String, shoeType: Int)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FilterListener
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss() // Đóng cửa sổ overlay khi nút được nhấn
        }


        viewModel.categories.observe(viewLifecycleOwner, Observer {
            binding.rvBrand.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val _adapter = FilterAdapter(it)
                adapter = _adapter
                _adapter.setOnItemListener(object : FilterAdapter.OnItemClickListener {
                    override fun onItemClick(selectedItems: Int) {
                        shoeType = selectedItems
                    }
                })

            }
        })
        viewModel.loadCategories(this.cateId)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.btnLowestPrice.id -> {
                    this.sortType = "price ASC"
                }
                binding.btnHighestPrice.id -> {
                    this.sortType = "price DESC"
                }
                binding.btnBestSeller.id -> {
                    this.sortType = ""
                }
                binding.btnLatest.id -> {
                    this.sortType = "inserted_at DESC"
                }
                else -> { this.sortType = "" }
            }
        }

        binding.btnApplyFilter.setOnClickListener() {
            listener?.onFilterSelected(sortType, shoeType)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels).toInt(), // 100% chiều rộng
            WindowManager.LayoutParams.MATCH_PARENT // Chiều cao là MATCH_PARENT (chiếm toàn bộ chiều cao màn hình)
        )
        // Di chuyển cửa sổ dialog gần mép trái
        dialog?.window?.setGravity(Gravity.START)
    }
}