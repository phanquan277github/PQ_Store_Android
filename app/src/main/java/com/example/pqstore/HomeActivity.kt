package com.example.pqstore

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pqstore.databinding.ActivityHomeBinding
import com.example.pqstore.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = MainActivity.auth.currentUser?.displayName

        binding.btnLogout.setOnClickListener() {
            // call requestIdToken as follows
            MainActivity.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}