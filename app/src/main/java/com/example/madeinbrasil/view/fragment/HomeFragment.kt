package com.example.madeinbrasil.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.adapter.HomeAdapter
import com.example.madeinbrasil.databinding.FragmentHomeBinding
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var binding: FragmentHomeBinding? = null

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter {
            val movieClicked = it
            movieClicked?.let{result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)

                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)
                startActivity(intent)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let{
            viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
            setupRecyclerView()
            loadContentNowPlaying()
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    private fun loadContentUpcoming() {
        viewModel.upcomingMoviePagedList?.observe(viewLifecycleOwner, { pagedList ->
            homeAdapter.submitList(pagedList)
            Log.i("R","->>$pagedList")
        })
    }

    private fun loadContentNowPlaying() {
        viewModel.nowPlayingMoviePagedList?.observe(viewLifecycleOwner, { pagedList ->
            homeAdapter.submitList(pagedList)
            Log.i("U","->>$pagedList")
        })
    }

    private fun setupRecyclerView() {
        binding?.rvCardsListLancamentos?.apply {
               layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL,false)
            adapter = homeAdapter
        }

        binding?.rvCardsListMovies?.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL,false)
            adapter = homeAdapter
        }

        binding?.rvCardListSeries?.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL,false)
            adapter = homeAdapter
        }
    }
}