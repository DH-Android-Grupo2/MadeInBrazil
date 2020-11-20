package com.example.madeinbrasil.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.FragmentSeriesBinding
import com.example.madeinbrasil.model.`class`.Series
import com.example.madeinbrasil.model.home.SeriesRepository
import com.example.madeinbrasil.view.adapter.MainAdapterSeries

class SeriesFragment : Fragment() {
    private var binding: FragmentSeriesBinding? = null
    private val seriesRepository: List<Series> = SeriesRepository().setSeries()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<RecyclerView>(R.id.rvCardsListSeries)?.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = MainAdapterSeries(seriesRepository)
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

}