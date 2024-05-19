package com.example.pqstore.authentications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pqstore.R
import com.example.pqstore.databinding.ActivityAuthenticationBinding
import com.example.pqstore.model.UserModel
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(SignInFragment())
        binding.btnSignInNavigation.setOnClickListener() {
            replaceFragment(SignInFragment())
        }
        binding.btnSignUpNavigation.setOnClickListener() {
            replaceFragment(SignUpFragment())
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}