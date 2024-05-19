package com.example.pqstore.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pqstore.R
import com.example.pqstore.databinding.ActivityHomeBinding
import com.example.pqstore.fragment.CartFragment
import com.example.pqstore.fragment.FavoriteFragment
import com.example.pqstore.fragment.HomeFragment
import com.example.pqstore.fragment.NotificationFragment
import com.example.pqstore.fragment.ProfileFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menuHome -> replaceFragment(HomeFragment())
                R.id.menuNotification -> replaceFragment(NotificationFragment())
                R.id.menuFarvorite -> replaceFragment(FavoriteFragment())
                R.id.menuCart -> replaceFragment(CartFragment())
                R.id.menuProfile -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameLayout.id, fragment)
        fragmentTransaction.commit()
    }
}