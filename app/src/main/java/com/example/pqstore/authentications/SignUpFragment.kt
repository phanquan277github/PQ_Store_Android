package com.example.pqstore.authentications

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pqstore.MainActivity
import com.example.pqstore.R
import com.example.pqstore.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    companion object {
        private const val TAG = "SignUpFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        binding.btnRegister.setOnClickListener() {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.error = "Định dạng email không hợp lệ"
            } else if (TextUtils.isEmpty(password)) {
                binding.edtPassword.error = "Vui lòng nhập mật khẩu"
            }else if(TextUtils.isEmpty(confirmPassword)){
                binding.edtConfirmPassword.error = "Vui lòng nhập lại mật khẩu"
            }else if(password != confirmPassword) {
                binding.edtConfirmPassword.error = "Mật khẩu nhập lại không khớp"
            }else if (password.length < 6){
                binding.edtPassword.error = "Mật khẩu phải có ít nhất 6 kí tự"
            }else{
                createAccount(email, password)
            }
        }

        return binding.root
    }

    private fun createAccount(email: String, password: String) {
        MainActivity.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                   Toast.makeText(context, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Xảy ra lỗi trong quá trình tạo. Vui lòng thử lại!",Toast.LENGTH_SHORT).show()
                }
            }
    }

}