package com.example.madeinbrasil.view.fragment


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.adapter.EpisodeAdapter
import com.example.madeinbrasil.databinding.FragmentEpisodesBinding
import com.example.madeinbrasil.model.seasons.Seasons
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_EPISODE_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EpisodesFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEpisodesBinding
    private var seasons: Seasons? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        seasons = bundle?.getParcelable(BASE_EPISODE_KEY)

        binding.rvCardsEpisodes?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = seasons?.episodes?.let { EpisodeAdapter(it) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentEpisodesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}