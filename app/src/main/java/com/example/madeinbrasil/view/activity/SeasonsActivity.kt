package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.adapter.SeasonsAdapter
import com.example.madeinbrasil.databinding.ActivitySeasonsBinding
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_EPISODE_KEY
import com.example.madeinbrasil.view.fragment.EpisodesFragment
import com.example.madeinbrasil.viewModel.SeasonsViewModel

class SeasonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeasonsBinding
    private var seasons: SerieDetailed? = null
    private var series: ResultSearch? = null
    private lateinit var viewModelSeason: SeasonsViewModel

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
                }
            }
        }


        viewModelSeason.seasonSucess?.observe(this@SeasonsActivity) { ep ->
            val bottomSheet = EpisodesFragment()
            val bundle = Bundle()
            bundle.putParcelable(BASE_EPISODE_KEY, ep)

            bottomSheet.arguments = bundle
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
}