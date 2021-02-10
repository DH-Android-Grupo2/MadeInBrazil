package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.SeasonsAdapter
import com.example.madeinbrasil.adapter.SeasonsDataBaseAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.season.EpisodeEntity
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.databinding.ActivitySeasonsBinding
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_EPISODE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SEASON_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SEASON_KEY_OFF
import com.example.madeinbrasil.view.fragment.EpisodesFragment
import com.example.madeinbrasil.viewModel.SeasonsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SeasonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeasonsBinding
    private var seasons: SerieDetailed? = null
    private var seasonId: Int = 0
    private lateinit var viewModelSeason: SeasonsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeasonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seasons = intent.getParcelableExtra(SEASON_KEY)

        binding.ivArrowBackSeasons.setOnClickListener {
            finish()
        }

        viewModelSeason = ViewModelProvider(this).get(SeasonsViewModel::class.java)

        if(seasons == null) {
            seasonId = intent.getIntExtra(SEASON_KEY_OFF, 0)
            val filter = MenuActivity.MIDIA.filter { it.id == seasonId }

            filter.forEach {midia ->
                midia.seasons?.forEach { season ->
                    val fil = FilmsAndSeriesActivity.SEASON.filter { it.id == season }

                    if(fil.isNotEmpty()) {
                        binding.rvCardsSeasons.apply {
                            layoutManager = LinearLayoutManager(this@SeasonsActivity)
                            adapter = SeasonsDataBaseAdapter(fil)
                        }
                    }
                }
            }

            Snackbar.make(binding.ivArrowBackSeasons, R.string.string_you_are_offline, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Entendido") {
                        it.isVisible = false
                    }.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.colorAccent))
                    .setTextColor(ContextCompat.getColor(this, R.color.colorPrimary)).show()
        }else {
            binding.rvCardsSeasons.apply {
                layoutManager = LinearLayoutManager(this@SeasonsActivity)
                adapter = seasons?.seasons?.let { season ->
                    SeasonsAdapter(season) {
                        viewModelSeason.getSeasons(seasons?.id, it?.season_number)
                    }
                }
            }
        }

        viewModelSeason.seasonSucess.observe(this@SeasonsActivity) { ep ->
            val bottomSheet = EpisodesFragment()
            val bundle = Bundle()

            bundle.putParcelable(BASE_EPISODE_KEY, ep)

            bottomSheet.arguments = bundle
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
}
