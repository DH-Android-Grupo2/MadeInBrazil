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
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.FilmsAdapter
import com.example.madeinbrasil.adapter.SeriesAdapter
import com.example.madeinbrasil.databinding.FragmentSeriesBinding
import com.example.madeinbrasil.model.home.SeriesRepository
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.view.adapter.MainAdapterSeries
import com.example.madeinbrasil.viewmodel.FilmsViewModel
import com.example.madeinbrasil.viewmodel.SeriesViewModel

class SeriesFragment : Fragment() {
    private var binding: FragmentSeriesBinding? = null
    private lateinit var viewModel: SeriesViewModel

    private val seriesAdapter : SeriesAdapter by lazy {
        SeriesAdapter {
            val serieClicked = it
            serieClicked?.let{result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)

                intent.putExtra(Constants.ConstantsFilms.BASE_SERIE_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 2)
                startActivity(intent)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetupSearchView()
        activity?.let{
            viewModel = ViewModelProvider(this).get(SeriesViewModel::class.java)
            setupRecyclerView()
            loadContentSearchMovie()
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


    private fun loadContentSearchMovie() {
        viewModel.searchSeriePagedList?.observe(viewLifecycleOwner, { pagedList ->
            seriesAdapter.submitList(pagedList)
        })
    }

    private fun setupRecyclerView() {
        binding?.rvCardsListSeries?.apply {
            layoutManager = GridLayoutManager(this@SeriesFragment.context,2)
            adapter = seriesAdapter
        }

    }

    fun SetupSearchView() {

        val searchView: SearchView? = binding?.tilSearchSeries

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
