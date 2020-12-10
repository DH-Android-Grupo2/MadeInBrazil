package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.adapter.SelectedShowsAdapter
import com.example.madeinbrasil.databinding.ActivityCreateListBinding
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_MOVIES
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_SERIES
import com.example.madeinbrasil.view.fragment.SelectMovieFragment
import com.example.madeinbrasil.view.fragment.SelectSerieFragment
import com.example.madeinbrasil.viewmodel.SelectMovieViewModel
import com.example.madeinbrasil.viewmodel.SelectSerieViewModel
import java.util.ArrayList

class CreateListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateListBinding
    private lateinit var selectSerieViewModel: SelectSerieViewModel
    private lateinit var selectMovieViewModel: SelectMovieViewModel
    private var selectedMovies: MutableList<Int> = mutableListOf()
    private var selectedSeries: MutableList<Int> = mutableListOf()

    private val selectedShowsAdapter by lazy {
        SelectedShowsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectSerieViewModel = ViewModelProvider(this).get(SelectSerieViewModel::class.java)
        selectMovieViewModel = ViewModelProvider(this).get(SelectMovieViewModel::class.java)
        setUpRecyclerView()
        setupButtonListeners()
        setupShowClickListeners()
    }

    private fun setUpRecyclerView() {
        binding.rvSelectedShows.apply {
            layoutManager = LinearLayoutManager(this@CreateListActivity, RecyclerView.HORIZONTAL, false)
            adapter = selectedShowsAdapter
        }
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
            fragment.arguments = Bundle().apply {
                putIntArray(SELECTED_SERIES, selectedSeries.toIntArray())
            }
            fragment.show(supportFragmentManager, null)
        }
    }

    private fun setupShowClickListeners() {
        selectMovieViewModel.clickedMovieItem.observe(this, {
            if (selectedMovies.contains(it.id)) {
                selectedMovies.remove(it.id)
                selectedShowsAdapter.deleteItem(it)
            }
            else {
                selectedMovies.add(it.id)
                selectedShowsAdapter.addItem(it)
            }
        })

        selectSerieViewModel.clickedSerieItem.observe(this, {
            if (selectedSeries.contains(it.id)) {
                selectedSeries.remove(it.id)
                selectedShowsAdapter.deleteItem(it)
            }
            else {
                selectedSeries.add(it.id)
                selectedShowsAdapter.addItem(it)
            }
        })

    }

}