package com.example.madeinbrasil.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.HomeAdapter
import com.example.madeinbrasil.adapter.MovieCreditsAdapter
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.extensions.getFirst4Chars
import com.example.madeinbrasil.model.home.CommentRepository
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_ACTOR_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.adapter.MainAdapterActors
import com.example.madeinbrasil.view.adapter.MainAdapterComments
import com.example.madeinbrasil.viewModel.TrailerViewModel
import com.example.madeinbrasil.viewmodel.GenderMovieViewModel
import com.example.madeinbrasil.viewmodel.MovieCreditsViewModel
import com.example.madeinbrasil.viewmodel.MovieDetailedViewModel

class FilmsAndSeriesActivity : AppCompatActivity() {

    private lateinit var viewModel: GenderMovieViewModel
    private lateinit var viewModelTrailer: TrailerViewModel
    private lateinit var viewModelMovie: MovieDetailedViewModel
    private lateinit var binding: ActivityFilmsAndSeriesBinding

    private var films: Result? = null
    private var filmDetailed: MovieDetailed?  = null
    private var series: ResultSearch? = null
    private var actors: List<Cast> = listOf()
    private var comments = CommentRepository().setComments()
    private var positionFragment = 0
    private lateinit var viewModelCast: MovieCreditsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFilmsAndSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        films = intent.getParcelableExtra(BASE_FILM_KEY)
        series = intent.getParcelableExtra(BASE_SERIE_KEY)
        positionFragment = intent.getIntExtra(ID_FRAGMENTS, 0)

        binding.ivArrowBackFilmsSeries.setOnClickListener {
            finish()
        }

        when(positionFragment) {
            1 -> {
                viewModelCast = ViewModelProvider(this).get(MovieCreditsViewModel::class.java)
                viewModelCast.getCredits(films?.id)

                viewModelMovie = ViewModelProvider(this).get(MovieDetailedViewModel::class.java)
                viewModelMovie.getMovie(films?.id)

                viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)

                viewModelTrailer = ViewModelProvider(this).get(TrailerViewModel::class.java)
                films?.let { viewModelTrailer.getTrailer(it.id) }

                viewModel.getGenres()
                setupObservables()
                Glide.with(this)
                        .load(films?.posterPath)
                        .into(binding.ivBannerFilmsSeries)
                films?.backdropPath?.let {
                    Glide.with(this)
                            .load(films?.backdropPath)
                            .into(binding.ivBackDropFilmSeries)
                }

                binding.tvDescriptionTextFilmsSeries.text = films?.overview
                binding.tvNameFilmsSeries.text = films?.title
                binding.tvNoteFilmsSeries.text = "${films?.voteAverage?.div(2)}"
                films?.voteAverage?.let {
                    binding.ratingBarFilmsSeries.rating = it / 2.0f
                    binding.ratingBarFilmsSeries.stepSize = .5f
                }

                viewModelMovie.movieSucess.observe(this, {
                    it?.let { movie ->
                        val horas = movie.runtime?.div(60)
                        val minutos = movie.runtime?.rem(60)
                        binding.tvTimeFilmsSeries.text = "${horas}h${minutos}min"
                        binding.rvCardsListActors.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = MovieCreditsAdapter(movie?.credits.cast){
                                    val castClicked = it
                                    castClicked?.let{result->
                                        val intent = Intent(this@FilmsAndSeriesActivity, PeopleActivity::class.java)
                                        intent.putExtra(BASE_ACTOR_KEY, result)
                                        startActivity(intent)
                                    }
                            }
                        }
                        filmDetailed = movie
                    }
                })
                binding.btWebSiteFilmsSeries.setOnClickListener {
                    val uri = Uri.parse("${filmDetailed?.homepage}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }

                binding.btTrailerFilmsSeries.setOnClickListener {
                    viewModelTrailer.trailerSucess.observe(this, {
                        it?.let { trailer ->
                            Log.i("TRAILER", trailer.key)
                        }
                    })
                }

                binding.tvYearFilmsSeries.text = "${films?.releaseDate?.getFirst4Chars()}"

                viewModelCast.onResultCredits?.observe(this) {

                }

                findViewById<RecyclerView>(R.id.rvCommentsUsers).apply {
                    layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity)
                    adapter = MainAdapterComments(comments)
                }
            }

            2 -> {
                viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)
                viewModel.getGenres()
                //setupObservables()

                series?.let {
                    Glide.with(binding.root.context)
                            .load(series?.posterPath)
                            .into(binding.ivBannerFilmsSeries)
                    series?.backdropPath?.let {
                        Glide.with(binding.root.context)
                                .load(series?.backdropPath)
                                .into(binding.ivBackDropFilmSeries)
                    }

                    binding.tvDescriptionTextFilmsSeries.text = series?.overview
                    binding.tvNameFilmsSeries.text = series?.name
                    binding.tvNoteFilmsSeries.text = "${(series?.voteAverage)?.div(2)}"
                    series?.voteAverage?.let {
                        binding.ratingBarFilmsSeries.rating = (it / 2.0f).toFloat()
                        binding.ratingBarFilmsSeries.stepSize = .5f
                    }

                    binding.tvYearFilmsSeries.text = "${series?.firstAirDate?.getFirst4Chars()}"
                }

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

    private fun setupObservables() {
        var generosText = ""
        viewModel.onResultGenres.observe(this, {
            it?.let { generos ->
                films?.genreIds?.forEach { genreFilm ->
                    generos.genres.forEach { genre ->
                        if (genre.id == genreFilm) {
                            generosText += "${genre.name}  "
                        }
                    }
                }

            }
            binding.tvGenderFilmsSeries.text = generosText
        })
    }


}
