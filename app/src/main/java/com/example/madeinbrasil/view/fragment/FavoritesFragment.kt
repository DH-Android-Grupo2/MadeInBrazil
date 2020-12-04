package com.example.madeinbrasil.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.FragmentFavoritesBinding
import com.example.madeinbrasil.model.home.FilmRepository
import com.example.madeinbrasil.view.adapter.MainAdapterFilm

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val listFilms = FilmRepository().setFilms()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<RecyclerView>(R.id.rvCardsListFavorites)?.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = MainAdapterFilm(listFilms) {

            }
        }

        initSpinner()
    }

    private fun initSpinner() {
        binding.spinnerFavorite.apply {

            adapter = activity?.let {
                ArrayAdapter.createFromResource(it,
                        R.array.spinner_options,
                        R.layout.custom_spinner).apply {
                    setDropDownViewResource(R.layout.custom_spinner_dropdown)
                }
            }

//            setBackgroundResource(R.drawable.custom_spinner)
        }
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