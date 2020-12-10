package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.madeinbrasil.databinding.ActivityCreateListBinding
import com.example.madeinbrasil.view.fragment.SelectMovieFragment
import com.example.madeinbrasil.view.fragment.SelectSerieFragment

class CreateListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateListBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        binding.imBackButton.setOnClickListener {
            super.onBackPressed()
        }

        binding.imAddMovie.setOnClickListener {
            val fragment = SelectMovieFragment()
            fragment.show(supportFragmentManager, null)
        }

        binding.imAddSerie.setOnClickListener{
            val fragment = SelectSerieFragment()
            fragment.show(supportFragmentManager, null)
        }
    }

}