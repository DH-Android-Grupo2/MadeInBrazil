package com.example.madeinbrasil.view.fragment

import android.content.Intent
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
import com.example.madeinbrasil.adapter.SerieAdapter
import com.example.madeinbrasil.databinding.FragmentSeriesBinding
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.viewModel.SerieViewModel

class SeriesFragment : Fragment() {
    private var binding: FragmentSeriesBinding? = null
    private lateinit var viewModel: SerieViewModel

    private val seriesAdapter: SerieAdapter by lazy {
        SerieAdapter {
            val serieClicked = it
            serieClicked?.let {result ->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_SERIE_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 2)
                startActivity(intent)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearchView()
        activity?.let {
            viewModel = ViewModelProvider(this).get(SerieViewModel::class.java)
            setUpRecyclerView()
            loadContentSerie()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    private fun setUpRecyclerView() {
        binding?.rvCardsListSeries?.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = seriesAdapter
        }
    }

    private fun loadContentSerie() {
        viewModel.searchSeriePagedList?.observe(viewLifecycleOwner) { pagedList ->
            seriesAdapter.submitList(pagedList)
        }
    }

    private fun setUpSearchView() {
        val searchView = binding?.tilSearchSeries

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setQuerySerie(query)
                setUpRecyclerView()
                loadContentSerie()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setQuerySerie(newText)
                setUpRecyclerView()
                loadContentSerie()
                return true
            }

        })
    }

}