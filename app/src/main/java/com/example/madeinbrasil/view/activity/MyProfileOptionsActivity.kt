package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityMyProfileOptionsBinding
import com.example.madeinbrasil.view.fragment.FavoritesFragment
import com.example.madeinbrasil.view.fragment.MyListsFragment
import com.example.madeinbrasil.view.fragment.WatchedFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_my_profile_options.*

class MyProfileOptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backButton = findViewById<ImageView>(R.id.ivArrowBackOptionsProfile)

        backButton.setOnClickListener {
            super.onBackPressed()
        }


        binding.tlOptionsProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        initFragments(MyListsFragment())
                    }
                    1 -> {
                        initFragments(WatchedFragment())
                    }
                    2 -> {
                        initFragments(FavoritesFragment())
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }



    private fun initFragments(fragment: Fragment) {
        val fragmentStart = supportFragmentManager.beginTransaction()
        fragmentStart.replace(R.id.flContainerOptionsProfile, fragment)
        fragmentStart.commit()
    }
}