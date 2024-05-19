package com.example.pqstore.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqstore.MainActivity
import com.example.pqstore.R
import com.example.pqstore.adapter.OrderItemsAdapter
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.databinding.ActivityOrderBinding
import com.example.pqstore.helper.Helper
import com.example.pqstore.model.MainViewModel
import com.example.pqstore.model.Message
import com.example.pqstore.overlayDialog.AddressOverlayDialog
import com.example.pqstore.overlayDialog.PaymentMethodsOverlayDialog
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private var viewModel = MainViewModel()
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)

        var addressId: Int = 0
        var paymentMethods: String = ""
        loadListItems()

        binding.txtAddress.setOnClickListener() {
            val dialog = AddressOverlayDialog()
            dialog.show(supportFragmentManager, "overlay_dialog")
        }

        supportFragmentManager.setFragmentResultListener("address_request_key", this, FragmentResultListener() { _, result ->
            result.getString("selected_address")?.let {
                binding.txtAddress.text = it
            }
            result.getInt("selected_id").let {
                addressId = it
            }
        })

        binding.txtPaymentMethods.setOnClickListener() {
            val dialog = PaymentMethodsOverlayDialog()
            dialog.show(supportFragmentManager, "overlay_dialog")
        }
        supportFragmentManager.setFragmentResultListener("payment_method_key", this, FragmentResultListener() { _, result ->
           result.getString("payment_method")?.let {
               paymentMethods = it
               if (it == "CashOnDelivery") {
                    binding.txtPaymentMethods.text = "Thanh toán khi nhận hàng"
                } else if (it == "CreditCard") {
                    binding.txtPaymentMethods.text = "Thẻ tín dụng/Ghi nợ"
                }
            }
        })

        binding.btnOrder.setOnClickListener() {
            val uid = MainActivity.auth.currentUser?.uid
            val note: String = binding.edtNote.text.toString()
            RetrofitClient.getRetrofitClient().createOrder(action="createOrder", uid=uid!!, addressId=addressId, paymentMethods=paymentMethods, note=note).enqueue(object:
                Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful) {
                        val mess: Message? = response.body()
                        if (mess!!.success == 1) {
                            deleteCart()
                        } else {
                            StyleableToast.makeText(context, "Đặt hàng thất bại. Vui lòng thử lại!", R.style.NotifyToast).show()
                        }
                    }
                }
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e("OrderActivity", t.localizedMessage)
                }
            })
        }

        binding.btnBack.setOnClickListener() {finish()}
        setContentView(binding.root)
    }

    private fun deleteCart() {
        val uid = MainActivity.auth.currentUser?.uid
        RetrofitClient.getRetrofitClient().deleteCart(action="deleteCart", uid=uid!!).enqueue(object:
            Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful) {
                    val mess: Message? = response.body()
                    if (mess!!.success == 1) {
                        startActivity(Intent(context, HomeActivity::class.java))
                    } else {
//                        StyleableToast.makeText(context, mess.message, R.style.NotifyToast).show()
                    }
                }
            }
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.e("SignInFragment", t.localizedMessage)
            }
        })
    }

    private fun loadListItems() {
        viewModel.cartItems.observe(this, Observer {
            binding.rvListItem.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvListItem.adapter = OrderItemsAdapter(it)

            var total: Double = 0.0
            for (i in it) {
                total += i.price * i.quantity
            }
            binding.txtTotal.text = Helper.formatCurrency(total)
        })
        viewModel.loadCartItems()
    }
}