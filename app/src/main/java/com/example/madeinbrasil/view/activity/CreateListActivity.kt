package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityCreateListBinding
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.MOVIE_TAG
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SERIE_TAG
import com.example.madeinbrasil.view.fragment.SelectShowsFragment

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

        binding.imAddMovie.setOnClickListener(setClickListener(MOVIE_TAG))

        binding.imAddSerie.setOnClickListener(setClickListener(SERIE_TAG))
    }

    private fun setClickListener(tag: String): View.OnClickListener {
        return View.OnClickListener {
            val fragment = SelectShowsFragment()
            fragment.show(supportFragmentManager, tag)
        }

    }
}