package com.example.pqstore.authentications

import android.content.Intent
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
import com.example.pqstore.activity.HomeActivity
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.databinding.FragmentSignUpBinding
import com.example.pqstore.model.Message
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.userProfileChangeRequest
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()

            if (TextUtils.isEmpty(name)) {
                binding.edtPassword.error = "Vui lòng nhập tên"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.error = "Định dạng email không hợp lệ"
            } else if (TextUtils.isEmpty(password)) {
                binding.edtPassword.error = "Vui lòng nhập mật khẩu"
            } else if(TextUtils.isEmpty(confirmPassword)){
                binding.edtConfirmPassword.error = "Vui lòng nhập lại mật khẩu"
            } else if(password != confirmPassword) {
                binding.edtConfirmPassword.error = "Mật khẩu nhập lại không khớp"
            } else if (password.length < 6){
                binding.edtPassword.error = "Mật khẩu phải có ít nhất 6 kí tự"
            } else {
                createAccount(name, email, password)
            }
        }

        return binding.root
    }

    private fun createAccount(name: String, email: String, password: String) {
        MainActivity.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = MainActivity.auth.currentUser
                    val uid = user?.uid
                    val photoUrl = if(user?.photoUrl != null ) user.displayName else ""

                    RetrofitClient.getRetrofitClient().authentication("login", uid=uid!!, email=email, name=name, avatar=photoUrl.toString()).enqueue(object:
                        Callback<Message> {
                        override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            if (response.isSuccessful) {
                                val mess: Message? = response.body()
                                if (mess!!.success == 1) {
                                    val profileUpdates = userProfileChangeRequest {
                                        this.displayName = name
                                    }
                                    user.updateProfile(profileUpdates).addOnCompleteListener { profileTask ->
                                        if (profileTask.isSuccessful) {
                                            Toast.makeText(context, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Log.e("SignInFragment", "Failed to update user profile.")
                                        }
                                    }
                                } else {
                                    StyleableToast.makeText(requireContext(), mess.message, R.style.NotifyToast).show()
                                }
                            }
                        }
                        override fun onFailure(call: Call<Message>, t: Throwable) {
                            Log.e("SignInFragment", t.localizedMessage)
                        }
                    })
                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception as FirebaseAuthException
                    val errorCode = exception.errorCode
                    if (errorCode == "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL") {
                        StyleableToast.makeText(requireContext(), "Email này đã được sử dụng trước đó!", R.style.ErrorToast).show()
                    } else {
                        StyleableToast.makeText(requireContext(), "Lỗi: " + errorCode, R.style.ErrorToast).show()
                    }
                }
            }
    }

}