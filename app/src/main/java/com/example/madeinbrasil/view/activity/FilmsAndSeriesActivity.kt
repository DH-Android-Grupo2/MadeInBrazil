package com.example.madeinbrasil.view.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.MovieCreditsAdapter
import com.example.madeinbrasil.adapter.MovieStreamingAdapter
import com.example.madeinbrasil.adapter.SerieCastAdapter
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.extensions.getFirst4Chars
import com.example.madeinbrasil.model.home.CommentRepository
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_ACTOR_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.adapter.MainAdapterComments
import com.example.madeinbrasil.viewModel.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import kotlinx.android.synthetic.main.youtube_popup.*
import com.example.madeinbrasil.viewModel.TrailerViewModel
import com.example.madeinbrasil.viewModel.GenderMovieViewModel
import com.example.madeinbrasil.viewModel.MovieCreditsViewModel
import com.example.madeinbrasil.viewmodel.MovieDetailedViewModel

class FilmsAndSeriesActivity : AppCompatActivity() {

    private lateinit var viewModel: GenderMovieViewModel
    private lateinit var viewModelTrailer: TrailerViewModel
    private lateinit var viewModelTrailerSeries: TrailerSeriesViewModel
    private lateinit var viewModelCastSerie: SerieCreditsViewModel
    private lateinit var viewModelCast: MovieCreditsViewModel
    private lateinit var viewModelMovie: MovieDetailedViewModel
    private lateinit var binding: ActivityFilmsAndSeriesBinding

    private var films: Result? = null
    private var filmDetailed: MovieDetailed?  = null
    private var series: ResultSearch? = null
    private var comments = CommentRepository().setComments()
    private var positionFragment = 0

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
                viewModelCast.getCredits(films?.id)
                viewModelTrailer.getTrailer(films?.id)
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
                    binding.ratingBarFilmsSeries.rating = it/2.0f
                    binding.ratingBarFilmsSeries.stepSize = .5f
                }

                viewModelMovie.movieSucess.observe(this, {
                    it?.let { movie ->
                        val horas = movie.runtime?.div(60)
                        val minutos = movie.runtime?.rem(60)
                        binding.tvTimeFilmsSeries.text = "${horas}h${minutos}min"
                        binding.rvCardsListActors.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = MovieCreditsAdapter(movie.credits.cast){
                                    val castClicked = it
                                    castClicked?.let{result->
                                        val intent = Intent(this@FilmsAndSeriesActivity, PeopleActivity::class.java)
                                        intent.putExtra(BASE_ACTOR_KEY, result)
                                        startActivity(intent)
                                    }
                            }
                        }
                        binding.rvStreaming.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = movie.watch_providers?.results?.BR?.let { it1 ->
                                MovieStreamingAdapter(it1.flatrate){

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

                        }
                    })
                }

                binding.tvYearFilmsSeries.text = "${films?.releaseDate?.getFirst4Chars()}"

                viewModelTrailer.trailerSucess?.observe(this) {trailer ->
                    trailer.results?.forEach {
                        binding.btTrailerFilmsSeries.isVisible = it.key != ""
                    }

                    binding.btTrailerFilmsSeries.setOnClickListener {
                        youtubeMovies(it)
                    }
                }

                /*viewModelCast.onResultCredits?.observe(this) {
                    it?.cast.let { cast ->
                        binding.rvCardsListActors.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = cast?.let { it1 -> MovieCreditsAdapter(it1) }
                        }
                    }
                }
                viewModelCast.onResultCredits?.observe(this) {

                }*/

                    findViewById<RecyclerView>(R.id.rvCommentsUsers).apply {
                        layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity)
                        adapter = MainAdapterComments(comments)
                    }
                }

            2 -> {
                viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)
                viewModelCastSerie = ViewModelProvider(this).get(SerieCreditsViewModel::class.java)
                viewModelTrailerSeries = ViewModelProvider(this).get(TrailerSeriesViewModel::class.java)
                viewModelCastSerie.getCreditsSerie(series?.id)
                viewModelTrailerSeries.getTrailerSerie(series?.id)
                viewModel.getGenres()

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

                viewModelTrailerSeries.trailerSucessSerie?.observe(this) {trailer ->
                    trailer.results?.forEach {
                        binding.btTrailerFilmsSeries.isVisible = it.key != ""
                    }

                    binding.btTrailerFilmsSeries.setOnClickListener {
                        youtubeSeries(it)
                    }
                }

                viewModelCastSerie.creditsSucess?.observe(this) {
                    it?.cast.let { castSerie ->
                        binding.rvCardsListActors.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = castSerie?.let { it1 -> SerieCastAdapter(it1) }
                        }
                    }
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
    private fun youtubeMovies(click: View) {
        val dialog = Dialog(click.context)
        dialog.setContentView(R.layout.youtube_popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val iFramePlayerOptions = IFramePlayerOptions.Builder().controls(1).build()
        var key = ""
        viewModelTrailer.trailerSucess.observe(this) { trailer ->
            trailer.results.forEach { key = it.key }
        }
        lifecycle.addObserver(dialog.youtubePlayerDialog)
        dialog.youtubePlayerDialog.initialize(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(lifecycle, key, 0.0f)
            }
        }, true, iFramePlayerOptions)

        dialog.show()
    }

    private fun youtubeSeries(click: View) {
        val dialog = Dialog(click.context)
        dialog.setContentView(R.layout.youtube_popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val iFramePlayerOptions = IFramePlayerOptions.Builder().controls(1).build()
        var key = ""
        viewModelTrailerSeries.trailerSucessSerie.observe(this) { trailer ->
            trailer.results.forEach { key = it.key }
        }
        lifecycle.addObserver(dialog.youtubePlayerDialog)
        dialog.youtubePlayerDialog.initialize(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(lifecycle, key, 0.0f)
            }
        }, true, iFramePlayerOptions)

        dialog.show()
    }
}
