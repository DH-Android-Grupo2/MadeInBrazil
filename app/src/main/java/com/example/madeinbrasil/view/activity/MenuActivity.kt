package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityMenuBinding
import com.example.madeinbrasil.view.fragment.FilmsFragment
import com.example.madeinbrasil.view.fragment.SeriesFragment

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.buttonHome -> false
                R.id.buttonFilms -> {
                    initFragments(FilmsFragment())
                    true
                }
                R.id.buttonLists -> false
                R.id.buttonSeries -> {
                    initFragments(SeriesFragment())
                    true
                }
                else -> false
            }
        }
        supportActionBar?.hide()
    }

    private fun initFragments(fragment: Fragment) {
        val fragmentStart = supportFragmentManager.beginTransaction()
        fragmentStart.replace(R.id.flContainerMenu, fragment)
        fragmentStart.commit()
    }
}