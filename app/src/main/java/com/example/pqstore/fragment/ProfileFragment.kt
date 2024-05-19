package com.example.pqstore.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pqstore.activity.EditProfileActivity
import com.example.pqstore.MainActivity
import com.example.pqstore.R
import com.example.pqstore.activity.UserAddressActivity
import com.example.pqstore.activity.UserOrdersActivity
import com.example.pqstore.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        val user = MainActivity.auth.currentUser

        Glide
            .with(binding.root)
            .load(user?.photoUrl.toString())
            .centerCrop()
            .placeholder(R.drawable.ic_profile)
            .into(binding.imgAvatar)
        binding.txtName.text = user?.displayName

        binding.editProfile.setOnClickListener() {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }
        binding.btnAddress.setOnClickListener() {
            startActivity(Intent(context, UserAddressActivity::class.java))
        }
        binding.btnMyOrder.setOnClickListener() {
            startActivity(Intent(context, UserOrdersActivity::class.java))
        }

        binding.btnLogout.setOnClickListener() {
            MainActivity.auth.signOut()
            startActivity(Intent(context, MainActivity::class.java))
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}