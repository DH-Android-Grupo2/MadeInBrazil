package com.example.madeinbrasil.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.FragmentSeriesBinding
import com.example.madeinbrasil.model.home.SeriesRepository
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.view.adapter.MainAdapterSeries

class SeriesFragment : Fragment() {
    private var binding: FragmentSeriesBinding? = null
    private val seriesRepository = SeriesRepository().setSeries()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<RecyclerView>(R.id.rvCardsListSeries)?.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = MainAdapterSeries(seriesRepository) {position ->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_SERIE_KEY, seriesRepository[position])
                intent.putExtra(ID_FRAGMENTS, 2)
                startActivity(intent)
            }
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