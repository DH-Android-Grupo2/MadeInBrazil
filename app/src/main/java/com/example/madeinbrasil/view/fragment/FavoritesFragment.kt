package com.example.madeinbrasil.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.FavoriteMidiaAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.databinding.FragmentFavoritesBinding
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.view.activity.MenuActivity
import kotlinx.coroutines.launch

class FavoritesFragment() : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private var favList = mutableListOf<MidiaFirebase>()
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

        MenuActivity.USER.favorites.forEach { fav ->
            val filter = MenuActivity.MIDIA.filter { it.id == fav }
            filter.forEach {
                favList.add(it)
            }
        }

        activity?.let {activity ->
            val db = MadeInBrazilDatabase.getDatabase(activity).favoriteDao()

            binding.rvCardsListFavorites.apply {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = FavoriteMidiaAdapter(favList) {midia ->
                    when(midia.midiaType) {
                        1 -> {
                            val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                            intent.putExtra(Constants.ConstantsFilms.BASE_MIDIA_KEY, midia)
                            intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)
                            startActivity(intent)
                        }
                        2 -> {
                            val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                            intent.putExtra(Constants.ConstantsFilms.BASE_MIDIA_KEY, midia)
                            intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 2)
                            startActivity(intent)
                        }
                    }
                }
            }

//            lifecycleScope.launch {
//                binding.rvCardsListFavorites.apply {
//                    layoutManager = GridLayoutManager(activity, 2)
//                    adapter = FavoriteMidiaAdapter(db.getMidiaWithFavorites())
//                }
//
//            }
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
        }
    }

}