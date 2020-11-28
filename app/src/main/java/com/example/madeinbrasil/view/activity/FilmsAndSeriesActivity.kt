package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.model.classe.Films
import com.example.madeinbrasil.model.classe.Series
import com.example.madeinbrasil.model.home.ActorsRepository
import com.example.madeinbrasil.model.home.CommentRepository
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.adapter.MainAdapterActors
import com.example.madeinbrasil.view.adapter.MainAdapterComments

class FilmsAndSeriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmsAndSeriesBinding
    private var films: Result? = null
    private var series: Series? = null
    private var actors = ActorsRepository().setActors()
    private var comments = CommentRepository().setComments()
    private var positionFragment = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmsAndSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        films = intent.getParcelableExtra(Constants.ConstantsFilms.BASE_FILM_KEY)
        Log.i("Resultado","${films?.backdropPath}")
        series = intent.getParcelableExtra(Constants.ConstantsFilms.BASE_SERIE_KEY)
        positionFragment = intent.getIntExtra(ID_FRAGMENTS, 0)

        if(positionFragment == 1) {
            Glide.with(this)
                .load(films?.posterPath)
                .into(binding.ivBannerFilmsSeries)
            films?.backdropPath?.let{
                Glide.with(this)
                    .load(films?.backdropPath)
                    .into(binding.ivBackDropFilmSeries)
            }

            binding.tvDescriptionTextFilmsSeries.text = films?.overview
            binding.tvNameFilmsSeries.text = films?.title
            binding.tvNoteFilmsSeries.text = "${films?.voteAverage}"
            films?.voteAverage?.let {
                binding.ratingBarFilmsSeries.rating = it
                binding.ratingBarFilmsSeries.stepSize = .5f

            }

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
