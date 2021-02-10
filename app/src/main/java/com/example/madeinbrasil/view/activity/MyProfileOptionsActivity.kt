package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityMyProfileOptionsBinding
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.VALUE
import com.example.madeinbrasil.view.fragment.FavoritesFragment
import com.example.madeinbrasil.view.fragment.HomeFragment
import com.example.madeinbrasil.view.fragment.MyListsFragment
import com.example.madeinbrasil.view.fragment.WatchedFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_my_profile_options.*

class MyProfileOptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileOptionsBinding
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivArrowBackOptionsProfile.setOnClickListener {
            super.onBackPressed()
        }
        position = intent.getIntExtra(VALUE, 0)

        when(position) {
            0 -> {
                initFragments(MyListsFragment())
                binding.tlOptionsProfile.getTabAt(0)?.select()
            }
            2 -> {
                initFragments(FavoritesFragment())
                binding.tlOptionsProfile.getTabAt(2)?.select()
            }
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