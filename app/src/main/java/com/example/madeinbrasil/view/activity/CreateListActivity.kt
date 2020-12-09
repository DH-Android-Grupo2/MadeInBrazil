package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.madeinbrasil.databinding.ActivityCreateListBinding
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_MOVIES
import com.example.madeinbrasil.view.fragment.SelectMovieFragment
import com.example.madeinbrasil.view.fragment.SelectSerieFragment
import com.example.madeinbrasil.viewmodel.SelectMovieViewModel
import com.example.madeinbrasil.viewmodel.SelectSerieViewModel

class CreateListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateListBinding
    private lateinit var selectSerieViewModel: SelectSerieViewModel
    private lateinit var selectMovieViewModel: SelectMovieViewModel
    private var selectedMovies: MutableList<Int> = mutableListOf()
    private var selectedSeries: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectSerieViewModel = ViewModelProvider(this).get(SelectSerieViewModel::class.java)
        selectMovieViewModel = ViewModelProvider(this).get(SelectMovieViewModel::class.java)
        setupButtonListeners()
        setupShowClickListeners()

    }

    private fun setupButtonListeners() {
        binding.imBackButton.setOnClickListener {
            super.onBackPressed()
        }

        binding.imAddMovie.setOnClickListener {
            val fragment = SelectMovieFragment()
            fragment.arguments = Bundle().apply {
                putIntArray(SELECTED_MOVIES, selectedMovies.toIntArray())
            }
            fragment.show(supportFragmentManager, null)
        }

        binding.imAddSerie.setOnClickListener{
            val fragment = SelectSerieFragment()
            fragment.show(supportFragmentManager, null)
        }
    }

    private fun setupShowClickListeners() {
        selectMovieViewModel.clickedItemId.observe(this, {
            selectedMovies.add(it)
            Log.i("ADDED", it.toString())
        })

    }

}