package com.example.madeinbrasil.view.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.SeasonsAdapter
import com.example.madeinbrasil.databinding.ActivitySeasonsBinding
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.seasons.Episode
import com.example.madeinbrasil.model.seasons.Seasons
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_EPISODE_KEY
import com.example.madeinbrasil.view.fragment.EpisodesFragment
import com.example.madeinbrasil.viewModel.SeasonsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SeasonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeasonsBinding
    private var seasons: SerieDetailed? = null
    private var series: ResultSearch? = null
    private lateinit var viewModelSeason: SeasonsViewModel
    private var seasonNumber: Int? = null
    private var episodes: Seasons? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeasonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seasons = intent.getParcelableExtra("season")
        series = intent.getParcelableExtra("serieId")

        binding.ivArrowBackSeasons.setOnClickListener {
            finish()
        }

        viewModelSeason = ViewModelProvider(this).get(SeasonsViewModel::class.java)

        binding.rvCardsSeasons?.apply {
            layoutManager = LinearLayoutManager(this@SeasonsActivity)
            adapter = seasons?.seasons?.let { season ->
                SeasonsAdapter(season) {
                    viewModelSeason.getSeasons(seasons?.id, it?.season_number)
                    val bottomSheet = EpisodesFragment()
                    viewModelSeason.seasonSucess?.observe(this@SeasonsActivity) { ep ->
                        val bundle = Bundle()
                        bundle.putParcelable(BASE_EPISODE_KEY, ep)

                        bottomSheet.arguments = bundle

                        if(!bottomSheet.isAdded && !bottomSheet.isVisible) {
                            val ft = supportFragmentManager.beginTransaction()
                            if(bottomSheet.tag != null) {
                                ft.remove(bottomSheet)
                            }
                            ft.remove(bottomSheet)
                            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                        }
                    }
                }
            }
        }
    }
}