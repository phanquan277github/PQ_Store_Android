package com.example.pqstore.overlayDialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.adapter.FilterAdapter
import com.example.pqstore.databinding.DialogFilterBinding

class FilterOverlayDialog : DialogFragment() {
    private lateinit var binding: DialogFilterBinding
    private var listener: FilterListener? = null

    interface FilterListener {
        fun onFilterSelected(filters: Map<String, String>)
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


//        val brand = ArrayList<String>()
//        brand.add("Nike")
//        brand.add("Adidas")
//        brand.add("Puma")
//        brand.add("Jordan")
//        binding.rvBrand.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvBrand.adapter = FilterAdapter(brand)


        binding.btnApplyFilter.setOnClickListener() {
//            val filters = mapOf("brand" to "")
//            listener?.onFilterSelected(filters)
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