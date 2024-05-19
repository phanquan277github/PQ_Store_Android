package com.example.pqstore.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pqstore.MainActivity
import com.example.pqstore.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = MainActivity.auth.currentUser
        binding.edtName.setText(user?.displayName)
        binding.edtEmail.setText(user?.email)

        binding.btnBack.setOnClickListener() {finish()}
    }
}