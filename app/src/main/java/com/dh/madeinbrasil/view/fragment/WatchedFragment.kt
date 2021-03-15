package com.dh.madeinbrasil.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dh.madeinbrasil.adapter.WatchedMidiaAdapter
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.databinding.FragmentWatchedBinding
import com.dh.madeinbrasil.utils.Constants
import com.dh.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.dh.madeinbrasil.view.activity.MenuActivity


class WatchedFragment : Fragment() {
    private lateinit var binding: FragmentWatchedBinding
    private var watchedList = mutableListOf<MidiaFirebase>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MenuActivity.USER.watched.forEach { watched ->
            val filter = MenuActivity.MIDIA.filter { it.id == watched }
            filter.forEach {
                watchedList.add(it)
            }
        }

        activity?.let {activity ->
            binding.rvCardsListWatched.apply {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = WatchedMidiaAdapter(watchedList) {midia ->
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}