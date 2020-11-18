package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madeinbrasil.R
import com.example.madeinbrasil.view.fragment.FilmsFragment
import com.example.madeinbrasil.view.fragment.SeriesFragment
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.button_home -> true
                R.id.button_films -> {
                    supportFragmentManager.beginTransaction().add(R.id.container, FilmsFragment()).commit()
                    true
                }
                R.id.button_lists -> true
                R.id.button_series -> {
                    supportFragmentManager.beginTransaction().add(R.id.container, SeriesFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}