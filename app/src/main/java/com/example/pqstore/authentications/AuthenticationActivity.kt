package com.example.pqstore.authentications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pqstore.HomeActivity
import com.example.pqstore.MainActivity
import com.example.pqstore.R
import com.example.pqstore.databinding.ActivityAuthenticationBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider

@Suppress("DEPRECATION")
class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    private lateinit var btnFb: LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_google_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnSignInGoogle.setOnClickListener() {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE)
        }

        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()
        binding.btnSignInFacebook.setReadPermissions("email", "public_profile")
        binding.btnSignInFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
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
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK || Xử lí auth của facebook
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Xử lí auth của google
        if (requestCode == RC_SIGN_IN_GOOGLE && resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        MainActivity.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Đăng nhập thành công bằng Google
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Lỗi GG", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // [START auth_with_facebook]
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        MainActivity.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Lỗi FB", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Lỗi handle " + it.toString() , Toast.LENGTH_SHORT).show()
                Log.e("DEBUG AUTHEN", it.toString())
            }
    }

    companion object {
        private const val TAG = "AuthenticationActivity"
        private const val RC_SIGN_IN_GOOGLE = 123
        private const val RC_SIGN_IN_FACEBOOK = 456
    }
}