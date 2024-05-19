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
import androidx.appcompat.app.AppCompatActivity
import com.example.pqstore.activity.HomeActivity
import com.example.pqstore.MainActivity
import com.example.pqstore.R
import com.example.pqstore.api.RetrofitClient
import com.example.pqstore.databinding.FragmentSignInBinding
import com.example.pqstore.model.Message
import com.example.pqstore.model.UserModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    companion object {
        private const val TAG = "SignInFragment"
        private const val RC_SIGN_IN_GOOGLE = 123
        private const val RC_SIGN_IN_FACEBOOK = 456
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_google_client_id))
            .requestEmail()
            .requestId()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        googleSignInClient.signOut()
        binding.btnSignInGoogle.setOnClickListener() {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE)
        }

        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logOut()
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }
            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }
            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        },)
        binding.btnSignInFacebook.setOnClickListener() {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"));
        }

        binding.btnSignInEmail.setOnClickListener() {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.error = "Định dạng email không hợp lệ"
            } else if (TextUtils.isEmpty(password)) {
                binding.edtPassword.error = "Vui lòng nhập mật khẩu"
            }else if (password.length < 6){
                binding.edtPassword.error = "Mật khẩu phải có ít nhất 6 kí tự"
            }else{
                signInWithEmail(email, password)
            }
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK || Xử lí auth của facebook
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Xử lí auth của google
        if (requestCode == RC_SIGN_IN_GOOGLE && resultCode == AppCompatActivity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        MainActivity.auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Đăng nhập thành công bằng Google
                    val user = MainActivity.auth.currentUser
                    val uid = user?.uid
                    val email = user?.email
                    val displayName = user?.displayName
                    val photoUrl = user?.photoUrl

                    RetrofitClient.getRetrofitClient().authentication("login", uid=uid!!, email=email!!, name=displayName!!, avatar=photoUrl.toString()).enqueue(object:
                        Callback<Message> {
                        override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            if (response.isSuccessful) {
                                val mess: Message? = response.body()
                                if (mess!!.success == 1) {
                                    startActivity(Intent(requireContext(), HomeActivity::class.java))
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
                        StyleableToast.makeText(requireContext(), "Email của tài khoản này đã được sử dụng trước đó!", R.style.ErrorToast).show()
                    } else {
                        StyleableToast.makeText(requireContext(), "Lỗi: " + errorCode, R.style.ErrorToast).show()
                    }
                }
            }
    }

    // [START auth_with_facebook]
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        MainActivity.auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = MainActivity.auth.currentUser
                    val uid = user?.uid
                    val email = user?.email
                    val displayName = user?.displayName
                    val photoUrl = user?.photoUrl

                    RetrofitClient.getRetrofitClient().authentication("login", uid=uid!!, email=email!!, name=displayName!!, avatar=photoUrl.toString()).enqueue(object:
                        Callback<Message> {
                        override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            if (response.isSuccessful) {
                                val mess: Message? = response.body()
                                if (mess!!.success == 1) {
                                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                                } else {
                                    StyleableToast.makeText(requireContext(), mess.message, R.style.NotifyToast).show()
                                }
                            }
                        }
                        override fun onFailure(call: Call<Message>, t: Throwable) {
                            Log.e("SignInFragment", t.localizedMessage)
                        }
                    })
                    startActivity(Intent(context, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception as FirebaseAuthException
                    val errorCode = exception.errorCode
                    if (errorCode == "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL") {
                        StyleableToast.makeText(requireContext(), "Email của tài khoản này đã được sử dụng trước đó!", R.style.ErrorToast).show()
                    } else {
                        StyleableToast.makeText(requireContext(), "Lỗi: " + errorCode, R.style.ErrorToast).show()
                    }
                }
            }
    }

    private fun signInWithEmail(email: String, password: String) {
        MainActivity.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = MainActivity.auth.currentUser
                    val uid = user?.uid
                    val displayName = if(user?.displayName != null ) user.displayName else ""
                    val photoUrl = if(user?.photoUrl != null ) user.displayName else ""

                    RetrofitClient.getRetrofitClient().authentication("login", uid=uid!!, email=email, name="$displayName", avatar=photoUrl.toString()).enqueue(object:
                        Callback<Message> {
                        override fun onResponse(call: Call<Message>, response: Response<Message>) {
                            if (response.isSuccessful) {
                                val mess: Message? = response.body()
                                if (mess!!.success == 1) {
                                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                                } else {
                                    StyleableToast.makeText(requireContext(), mess.message, R.style.NotifyToast).show()
                                }
                            }
                        }
                        override fun onFailure(call: Call<Message>, t: Throwable) {
                            Log.e("SignInFragment", t.localizedMessage)
                        }
                    })
                    startActivity(Intent(context, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception as FirebaseAuthException
                    val errorCode = exception.errorCode
                    if (errorCode == "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL") {
                        StyleableToast.makeText(requireContext(), "Email của tài khoản này đã được sử dụng trước đó!", R.style.ErrorToast).show()
                    } else {
                        StyleableToast.makeText(requireContext(), "Lỗi: " + errorCode, R.style.ErrorToast).show()
                    }
                }
            }
    }
}