package com.example.madeinbrasil.view.fragment

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
import com.example.madeinbrasil.databinding.FragmentFavoritesBinding
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
            val db = MadeInBrazilDatabase.getDatabase(activity).favoriteMidiaDao()

            lifecycleScope.launch {
                binding.rvCardsListFavorites.apply {
                    layoutManager = GridLayoutManager(activity, 2)
                    adapter = FavoriteMidiaAdapter(db.getMidiaWithFavorites())
                }

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
        }
    }

}