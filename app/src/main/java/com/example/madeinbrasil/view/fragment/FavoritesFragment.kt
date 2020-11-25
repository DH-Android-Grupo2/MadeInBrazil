package com.example.madeinbrasil.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.FragmentFavoritesBinding
import com.example.madeinbrasil.model.`class`.Films
import com.example.madeinbrasil.model.`class`.Series
import com.example.madeinbrasil.model.home.FilmRepository
import com.example.madeinbrasil.model.home.SeriesRepository
import com.example.madeinbrasil.view.adapter.MainAdapterFilm

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        activity?.findViewById<RecyclerView>(R.id.rvCardsListFavorites)?.apply {
//            layoutManager = LinearLayoutManager(activity)
//            adapter = MainAdapterFilm()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}