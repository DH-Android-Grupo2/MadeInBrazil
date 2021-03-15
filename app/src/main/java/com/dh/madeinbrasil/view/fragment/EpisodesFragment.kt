package com.dh.madeinbrasil.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dh.madeinbrasil.adapter.EpisodeAdapter
import com.dh.madeinbrasil.databinding.FragmentEpisodesBinding
import com.dh.madeinbrasil.model.seasons.Seasons
import com.dh.madeinbrasil.utils.Constants.ConstantsFilms.BASE_EPISODE_KEY
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