package com.example.pqstore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pqstore.activity.HomeActivity
import com.example.pqstore.authentications.AuthenticationActivity
import com.example.pqstore.databinding.ActivityMainBinding
import com.facebook.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.*

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this) // khởi tạo facebook SDK lần đầu
        FirebaseApp.initializeApp(this) // khởi tạo Firebase

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
//
////        binding = ActivityMainBinding.inflate(layoutInflater)
////        setContentView(binding.root)
//        startActivity(Intent(this, HomeActivity::class.java))
    }

}