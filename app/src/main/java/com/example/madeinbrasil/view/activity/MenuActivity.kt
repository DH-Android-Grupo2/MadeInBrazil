package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityMenuBinding
import com.example.madeinbrasil.view.fragment.FilmsFragment
import com.example.madeinbrasil.view.fragment.SeriesFragment
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.button_home -> false
                R.id.button_films -> {
                    supportFragmentManager.beginTransaction().add(R.id.container, FilmsFragment()).commit()
                    true
                }
                R.id.button_lists -> false
                R.id.button_series -> {
                    supportFragmentManager.beginTransaction().add(R.id.container, SeriesFragment()).commit()
                    true
                }
                else -> false
            }
        }
        supportActionBar?.hide()
    }
}