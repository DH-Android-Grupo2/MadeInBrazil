package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.model.classe.Films
import com.example.madeinbrasil.model.classe.Series
import com.example.madeinbrasil.model.home.ActorsRepository
import com.example.madeinbrasil.model.home.CommentRepository
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.adapter.MainAdapterActors
import com.example.madeinbrasil.view.adapter.MainAdapterComments

class FilmsAndSeriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmsAndSeriesBinding
    private var films: Films? = null
    private var series: Series? = null
    private var actors = ActorsRepository().setActors()
    private var comments = CommentRepository().setComments()
    private var positionFragment = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmsAndSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        films = intent.getParcelableExtra(Constants.ConstantsFilms.BASE_FILM_KEY)
        series = intent.getParcelableExtra(Constants.ConstantsFilms.BASE_SERIE_KEY)
        positionFragment = intent.getIntExtra(ID_FRAGMENTS, 0)

        if(positionFragment == 1) {
            Glide.with(this).load(films?.img).into(binding.ivBannerFilmsSeries)
            binding.tvDescriptionTextFilmsSeries.text = films?.description
            binding.tvNameFilmsSeries.text = films?.name

            findViewById<RecyclerView>(R.id.rvCardsListActors).apply {
                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = MainAdapterActors(actors)
            }

            findViewById<RecyclerView>(R.id.rvCommentsUsers).apply {
                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity)
                adapter = MainAdapterComments(comments)
            }
        }else {
            Glide.with(this).load(series?.img).into(binding.ivBannerFilmsSeries)

            findViewById<RecyclerView>(R.id.rvCardsListActors).apply {
                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = MainAdapterActors(actors)
            }

            findViewById<RecyclerView>(R.id.rvCommentsUsers).apply {
                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity)
                adapter = MainAdapterComments(comments)
            }
        }
    }
}
