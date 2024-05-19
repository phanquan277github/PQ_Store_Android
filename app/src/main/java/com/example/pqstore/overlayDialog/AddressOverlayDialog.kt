package com.example.pqstore.overlayDialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.adapter.OrderItemsAdapter
import com.example.pqstore.adapter.UserAddressAdapter
import com.example.pqstore.databinding.DialogAddressBinding
import com.example.pqstore.model.MainViewModel

class AddressOverlayDialog : DialogFragment(), UserAddressAdapter.OnAddressClickListener {
    private lateinit var binding: DialogAddressBinding
    private var viewModel = MainViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss() // Đóng cửa sổ overlay khi nút được nhấn
        }
        viewModel.address.observe(this, Observer {
            binding.rvAddress.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvAddress.adapter = UserAddressAdapter(it, this)
        })
        viewModel.loadUserAddress()
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

    override fun onAddressClick(id: Int, address: String) {
        val result = Bundle().apply {
            putInt("selected_id", id)
            putString("selected_address", address)
        }
        setFragmentResult("address_request_key", result)
        dismiss()
    }
}