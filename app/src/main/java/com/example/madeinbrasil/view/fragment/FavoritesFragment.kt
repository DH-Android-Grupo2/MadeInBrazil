package com.example.madeinbrasil.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.FavoriteMovieAdapter
import com.example.madeinbrasil.adapter.FavoriteMovieAndSerieAdapter
import com.example.madeinbrasil.adapter.FavoriteSerieAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.FavoritesMovieDetailed
import com.example.madeinbrasil.databinding.FragmentFavoritesBinding
import com.example.madeinbrasil.database.entities.favorites.FavoritesSerieDetailed
import com.example.madeinbrasil.model.home.FilmRepository
import com.example.madeinbrasil.viewModel.SerieDetailedViewModel
import kotlinx.coroutines.launch

class FavoritesFragment() : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    var actionMode: ActionMode? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {activity ->
            val db = MadeInBrazilDatabase.getDatabase(activity).favoriteDao()

            lifecycleScope.launch {
                binding.rvCardsListFavorites.apply {
                    layoutManager = GridLayoutManager(activity, 2)
                    adapter = FavoriteMovieAdapter(db.getMovieFavorites())
                }

//                binding.rvCardsListFavorites.apply {
//                    layoutManager = GridLayoutManager(activity, 2)
//                    adapter = FavoriteMovieAndSerieAdapter(db.getSerieFavorites(), db.getMovieFavorites())
//                }

//                binding.rvCardsListFavorites.apply {
//                    layoutManager = GridLayoutManager(activity, 2)
//                    adapter = FavoriteMovieAdapter(db.getMovieFavorites())
//                }

            }
        }

        initSpinner()
    }

    private fun initSpinner() {
        binding.spinnerFavorite.apply {

            adapter = activity?.let {
                val db = MadeInBrazilDatabase.getDatabase(it).favoriteDao()
                ArrayAdapter.createFromResource(it,
                        R.array.spinner_options,
                        R.layout.custom_spinner).apply {
                    setDropDownViewResource(R.layout.custom_spinner_dropdown)
                }
            }

        }
    }

}