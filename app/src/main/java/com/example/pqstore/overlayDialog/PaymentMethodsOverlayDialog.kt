package com.example.pqstore.overlayDialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.adapter.OrderItemsAdapter
import com.example.pqstore.adapter.UserAddressAdapter
import com.example.pqstore.databinding.DialogAddressBinding
import com.example.pqstore.databinding.DialogPaymentMethodsBinding
import com.example.pqstore.model.MainViewModel

class PaymentMethodsOverlayDialog : DialogFragment() {
    private lateinit var binding: DialogPaymentMethodsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogPaymentMethodsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss() // Đóng cửa sổ overlay khi nút được nhấn
        }

        binding.btnCashOnDelivery.setOnClickListener() {
            val result = Bundle().apply {
                putString("payment_method", "CashOnDelivery") // Thanh toán khi nhận hàng
            }
            setFragmentResult("payment_method_key", result)
            dismiss()
        }
        binding.btnCreditCard.setOnClickListener() {
            val result = Bundle().apply {
                putString("payment_method", "CreditCard") // Thẻ tín dụng/Ghi nợ
            }
            setFragmentResult("payment_method_key", result)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.heightPixels * 0.5).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
    }
}