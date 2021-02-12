package com.example.madeinbrasil.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.view.ActionMode
import androidx.core.widget.addTextChangedListener
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
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class FavoritesFragment() : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private var favList = mutableListOf<MidiaFirebase>()
    var actionMode: ActionMode? = null
    var start = 0

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

        if(start == 0) {
            setUpRecyclerView(null)
            start++
        }

        activity?.let {activity ->
            binding.tietSearchFavorites.addTextChangedListener (object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    setUpRecyclerView(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun setUpRecyclerView(text: String?) {
        binding.rvCardsListFavorites.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = FavoriteMidiaAdapter(filteredList(favList, text)) {midia ->
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
    }

    private fun filteredList(doc: List<MidiaFirebase>, text: String?): List<MidiaFirebase> {
        val filteredList = ArrayList<MidiaFirebase>()
        return if(text.isNullOrBlank()) {
            doc
        }else {
            for(i in doc) {
                if(i.toString().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(i)
                }
            }
            filteredList
        }
    }
}