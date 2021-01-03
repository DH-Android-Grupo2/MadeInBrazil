package com.example.madeinbrasil.view.fragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.DiscoverTvAdapter
import com.example.madeinbrasil.adapter.HomeAdapter
import com.example.madeinbrasil.databinding.FragmentHomeBinding
import com.example.madeinbrasil.model.discover.DiscoverMovie
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var binding: FragmentHomeBinding? = null
    var movieComplete: MovieDetailed? = null
    private  var selected: GenreSelected? = null
    private var discover: DiscoverMovie? = null
    companion object {
         var genre : GenreSelected? = null
    }
    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter { it: Result?, imageView: ImageView? ->
            val movieClicked = it
            movieClicked?.let{ result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)

                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imageView,"sharedImgView")
                startActivity(intent,options.toBundle())
            }

        }
    }

    private val homeAdapter2 : HomeAdapter by lazy {
        HomeAdapter { it: Result?, imageView: ImageView? ->
            val movieClicked = it
            movieClicked?.let{ result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)

                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imageView,"sharedImgView")
                startActivity(intent,options.toBundle())
            }

        }
    }

    private val homeAdapter3 : HomeAdapter by lazy {
        HomeAdapter { it: Result?, imageView: ImageView? ->
            val movieClicked = it
            movieClicked?.let{ result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)

                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imageView,"sharedImgView")
                startActivity(intent,options.toBundle())
            }

        }
    }

    private val homeAdapter4 : DiscoverTvAdapter by lazy {
        DiscoverTvAdapter { result ->
            result?.let {
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(Constants.ConstantsFilms.BASE_SERIE_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 2)
                startActivity(intent)
            }
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       selected = arguments?.getParcelable<GenreSelected>("Selected")
        genre = selected

       activity?.let{
            viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
            setupRecyclerView()
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
        viewModel.upcomingMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            homeAdapter2.currentList?.clear()
            homeAdapter2.submitList(pagedList, null)
            homeAdapter2.notifyDataSetChanged()
        }
    }

    private fun loadContentNowPlaying() {
        viewModel.nowPlayingMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            homeAdapter.currentList?.clear()
            homeAdapter.submitList(pagedList, null)
            homeAdapter.notifyDataSetChanged()
        }


    }

    private fun loadContentDiscoverMovie() {
        viewModel.discoverMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            homeAdapter3.currentList?.clear()
            homeAdapter3.submitList(pagedList, null)
            homeAdapter3.notifyDataSetChanged()

        }
    }

    private fun loadContentDiscoverTv() {
        viewModel.discoverTvPagedList?.observe(viewLifecycleOwner) { pagedList ->
            homeAdapter4.currentList?.clear()
            homeAdapter4.submitList(pagedList, null)
            homeAdapter4.notifyDataSetChanged()

        }
    }

    private fun setupRecyclerView() {
        binding?.rvCardsListLancamentos?.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentNowPlaying()
            adapter = homeAdapter

        }

        binding?.rvCardsListFuturosLancamentos?.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentUpcoming()

            adapter = homeAdapter2
        }

       binding?.rvCardsListMovies?.apply {
         layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
           loadContentDiscoverMovie()
           adapter = homeAdapter3
         }

        binding?.rvCardListSeries?.apply {
           layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentDiscoverTv()
            adapter = homeAdapter4
        }
    }
}