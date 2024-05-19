package com.example.pqstore.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pqstore.MainActivity
import com.example.pqstore.R
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.databinding.ActivityAddAddressBinding
import com.example.pqstore.model.Message
import com.example.pqstore.model.UserAddressModel
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAddressBinding
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener() {finish()}

        val type = intent.getStringExtra("type")
        var id = "0"
        if (type == "createAddress") {
            binding.txtTitle.text = "Thêm địa chỉ mới"
        } else if (type == "updateAddress") {
            binding.txtTitle.text = "Cập nhật địa chỉ"
            id = intent.getStringExtra("id").toString()
            binding.edtName.setText(intent.getStringExtra("name"))
            binding.edtPhone.setText(intent.getStringExtra("phone"))
            binding.edtStreet.setText(intent.getStringExtra("street"))
            binding.txtAddress.text = intent.getStringExtra("address")
        }

        binding.addressContainer.setOnClickListener() {
            val intent = Intent(this, SelectProvinceActivity::class.java)
            startActivityForResult(intent, 1)
        }

        binding.btnUpdate.setOnClickListener() {
            val name = binding.edtName.text.toString()
            val phone = binding.edtPhone.text.toString()
            val address = binding.txtAddress.text.toString()
            val street = binding.edtStreet.text.toString()
            val uid = MainActivity.auth.currentUser?.uid
            RetrofitClient.getRetrofitClient().createAddress( action=type!!, uid=uid!!, name=name, address=address, street=street, phone=phone, id=id!!.toInt()).enqueue(object:
                Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful) {
                        val mess: Message? = response.body()
                        if (mess!!.success == 1) {
                            startActivity(Intent(context, UserAddressActivity::class.java))
                        } else {
                            StyleableToast.makeText(context, mess.message, R.style.NotifyToast).show()
                        }
                    }
                }
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e("SignInFragment", t.localizedMessage)
                }
            })
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val address = data?.getStringExtra("address")
            binding.txtAddress.text = "$address"
        }
    }
}