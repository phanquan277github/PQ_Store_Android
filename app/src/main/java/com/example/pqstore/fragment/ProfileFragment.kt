package com.example.pqstore.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pqstore.EditProfileActivity
import com.example.pqstore.R
import com.example.pqstore.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        binding.editProfile.setOnClickListener() {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}