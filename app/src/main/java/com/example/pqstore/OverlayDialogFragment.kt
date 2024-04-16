package com.example.pqstore

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.pqstore.databinding.LayoutFilterPopupBinding

class OverlayDialogFragment : DialogFragment() {
    private lateinit var binding: LayoutFilterPopupBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutFilterPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss() // Đóng cửa sổ overlay khi nút được nhấn
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