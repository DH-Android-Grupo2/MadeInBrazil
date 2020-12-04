package com.example.madeinbrasil.view.fragment

import android.content.Intent
import android.icu.text.IDNA
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
//import com.airbnb.lottie.LottieAnimationView
import com.example.madeinbrasil.adapter.FilmsAdapter
import com.example.madeinbrasil.databinding.FragmentFilmsBinding
import com.example.madeinbrasil.model.home.FilmRepository
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.viewModel.FilmsViewModel
import kotlinx.android.synthetic.main.fragment_films.*
import java.util.*


class FilmsFragment : Fragment() {
    private var binding: FragmentFilmsBinding? = null
    private lateinit var viewModel: FilmsViewModel

    private val filmsAdapter : FilmsAdapter by lazy {
        FilmsAdapter {
            val movieClicked = it
            movieClicked?.let{result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_FILM_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 1)
                startActivity(intent)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetupSearchView()
        activity?.let{
            viewModel = ViewModelProvider(this).get(FilmsViewModel::class.java)
            setupRecyclerView()
            loadContentSearchMovie()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    private fun loadContentSearchMovie() {
        viewModel.searchMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            filmsAdapter.submitList(pagedList)
        }
    }

    private fun setupRecyclerView() {
        binding?.rvCardsListFilms?.apply {
            layoutManager = GridLayoutManager(this@FilmsFragment.context,2)
            adapter = filmsAdapter
        }

    }

    private fun SetupSearchView() {

        val searchView:SearchView? = binding?.tilSearchFilms

        searchView?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String):Boolean {
                viewModel.setQuery(query)
                Log.i("Query","${viewModel.getQuery()}")
                setupRecyclerView()
                loadContentSearchMovie()
                return true
            }
            override fun onQueryTextChange(newText: String):Boolean{
                viewModel.setQuery(newText)
                Log.i("Query","${viewModel.getQuery()}")
                setupRecyclerView()
                loadContentSearchMovie()
               return true
            }
        })
    }

}

