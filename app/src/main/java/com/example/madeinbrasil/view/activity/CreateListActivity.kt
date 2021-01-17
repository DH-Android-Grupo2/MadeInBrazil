package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.adapter.SelectedShowsAdapter
import com.example.madeinbrasil.databinding.ActivityCreateListBinding
import com.example.madeinbrasil.model.customLists.CustomList
import com.example.madeinbrasil.model.customLists.relation.ListWithMedia
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_MOVIES
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_SERIES
import com.example.madeinbrasil.view.fragment.SelectMovieFragment
import com.example.madeinbrasil.view.fragment.SelectSerieFragment
import com.example.madeinbrasil.viewModel.CustomListViewModel
import com.example.madeinbrasil.viewModel.SelectMovieViewModel
import com.example.madeinbrasil.viewModel.SelectSerieViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_create_list.*
import java.util.ArrayList

class CreateListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateListBinding
    private lateinit var selectSerieViewModel: SelectSerieViewModel
    private lateinit var selectMovieViewModel: SelectMovieViewModel
    private lateinit var customListViewModel: CustomListViewModel
    private var selectedMovies: MutableList<Int> = mutableListOf()
    private var selectedSeries: MutableList<Int> = mutableListOf()

    private val selectedShowsAdapter by lazy {
        SelectedShowsAdapter() {
            when(it) {
                is Result -> selectedMovies.remove(it.id)
                is ResultSearch -> selectedSeries.remove(it.id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customListViewModel = ViewModelProvider(this).get(CustomListViewModel::class.java)
        selectSerieViewModel = ViewModelProvider(this).get(SelectSerieViewModel::class.java)
        selectMovieViewModel = ViewModelProvider(this).get(SelectMovieViewModel::class.java)
        setupRecyclerView()
        setupButtonListeners()
        setupShowClickListeners()
        setupFormFieldsListeners()
    }

    private fun setupRecyclerView() {
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

        binding.btnCreateList.setOnClickListener {
            customListViewModel.createCustomList(ListWithMedia(CustomList(0, binding.teetName.text.toString(), binding.teetDescription.text.toString(), 0), listOf(), listOf()))
        }
    }

    private fun setupShowClickListeners() {
        selectMovieViewModel.clickedMovieItem.observe(this) {
            if (selectedMovies.contains(it.id)) {
                selectedMovies.remove(it.id)
                selectedShowsAdapter.deleteItem(it)
            } else {
                selectedMovies.add(it.id)
                selectedShowsAdapter.addItem(it)
            }
        }

        selectSerieViewModel.clickedSerieItem.observe(this) {
            if (selectedSeries.contains(it.id)) {
                selectedSeries.remove(it.id)
                selectedShowsAdapter.deleteItem(it)
            } else {
                selectedSeries.add(it.id)
                selectedShowsAdapter.addItem(it)
            }
        }

    }

    private fun setupFormFieldsListeners() {

        val listNameField: EditText = binding.teetName
        val createListBtn = binding.btnCreateList

        listNameField.addTextChangedListener {
            it?.let {
                createListBtn.isEnabled = it.trim().isNotEmpty()
            }
        }
    }

}