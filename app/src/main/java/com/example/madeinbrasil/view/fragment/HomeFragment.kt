package com.example.madeinbrasil.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.adapter.HomeAdapter
import com.example.madeinbrasil.databinding.FragmentHomeBinding
import com.example.madeinbrasil.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var binding: FragmentHomeBinding? = null

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter {
            val movieClicked = it
            movieClicked?.let{result->
                Log.i("${result.id}","Resultado")
            }

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let{
            setupRecyclerView()
            loadContent()
        }
        Log.i("$homeAdapter","Adapter")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    private fun loadContent() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.moviePagedList?.observe(viewLifecycleOwner, { pagedList ->
            homeAdapter.submitList(pagedList)
        })
    }

    private fun setupRecyclerView() {
        binding?.rvCardsListLancamentos?.apply {
               layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL,false)
               adapter = homeAdapter

        }

        binding?.rvCardListSeries?.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL,false)
            adapter = homeAdapter

        }

        binding?.rvCardsListMovies?.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL,false)
            adapter = homeAdapter

        }
    }

}