package com.example.madeinbrasil.view.fragment

import android.content.Intent
import android.icu.text.IDNA
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.FragmentFilmsBinding
import com.example.madeinbrasil.model.home.FilmRepository
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.view.adapter.MainAdapterFilm

class FilmsFragment : Fragment() {
    private var binding: FragmentFilmsBinding? = null
    private val filmsRepository = FilmRepository().setFilms()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<RecyclerView>(R.id.rvCardsListFilms)?.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = MainAdapterFilm(filmsRepository) {position ->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_FILM_KEY, filmsRepository[position])
                intent.putExtra(ID_FRAGMENTS, 1)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

}