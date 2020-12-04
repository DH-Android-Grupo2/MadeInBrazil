package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.extensions.getFirst4Chars
import com.example.madeinbrasil.model.classe.Films
import com.example.madeinbrasil.model.classe.Series
import com.example.madeinbrasil.model.gender.GenderMovie
import com.example.madeinbrasil.model.gender.Genre
import com.example.madeinbrasil.model.home.ActorsRepository
import com.example.madeinbrasil.model.home.CommentRepository
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.adapter.MainAdapterActors
import com.example.madeinbrasil.view.adapter.MainAdapterComments
import com.example.madeinbrasil.viewmodel.GenderMovieViewModel

class FilmsAndSeriesActivity : AppCompatActivity() {

    private lateinit var viewModel: GenderMovieViewModel
    private lateinit var binding: ActivityFilmsAndSeriesBinding
    private var films: Result? = null
    private var series: Result? = null
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

        binding.ivArrowBackFilmsSeries.setOnClickListener {
            finish()
        }

        binding.ratingBarFilmsSeries.rating = 5.0f

        if(positionFragment == 1) {
            viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)
            viewModel.getGenres()
            //setupObservables()

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
                binding.ratingBarFilmsSeries.rating = it/2.0f
                binding.ratingBarFilmsSeries.stepSize = .5f
            }

            binding.tvYearFilmsSeries.text = "${films?.releaseDate?.getFirst4Chars()}"


            findViewById<RecyclerView>(R.id.rvCardsListActors).apply {
                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = MainAdapterActors(actors)
            }

            findViewById<RecyclerView>(R.id.rvCommentsUsers).apply {
                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity)
                adapter = MainAdapterComments(comments)
            }

        }else {

            Glide.with(this)
                .load(series?.posterPath)
                .into(binding.ivBannerFilmsSeries)
            series?.backdropPath?.let{
                Glide.with(this)
                    .load(series?.backdropPath)
                    .into(binding.ivBackDropFilmSeries)
            }


            binding.tvDescriptionTextFilmsSeries.text = series?.overview
            binding.tvNameFilmsSeries.text = series?.name
            binding.tvNoteFilmsSeries.text = "${series?.voteAverage}"
            series?.voteAverage?.let {
                binding.ratingBarFilmsSeries.rating = it/2.0f
                binding.ratingBarFilmsSeries.stepSize = .5f
            }

            binding.tvYearFilmsSeries.text = "${series?.releaseDate?.getFirst4Chars()}"

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
