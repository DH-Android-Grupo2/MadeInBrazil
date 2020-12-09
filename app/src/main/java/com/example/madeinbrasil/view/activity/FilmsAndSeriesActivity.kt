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
import com.example.madeinbrasil.adapter.SerieStreamingAdapter
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.extensions.getFirst4Chars
import com.example.madeinbrasil.model.home.CommentRepository
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
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
import com.example.madeinbrasil.viewModel.GenderMovieViewModel
import com.example.madeinbrasil.viewModel.MovieCreditsViewModel
import com.example.madeinbrasil.viewModel.MovieDetailedViewModel

class FilmsAndSeriesActivity : AppCompatActivity() {

    private lateinit var viewModel: GenderMovieViewModel
//    private lateinit var viewModelCast: MovieCreditsViewModel
    private lateinit var viewModelMovie: MovieDetailedViewModel
    private lateinit var viewModelSerie: SerieDetailedViewModel
    private lateinit var binding: ActivityFilmsAndSeriesBinding

    private var films: Result? = null
    private var filmDetailed: MovieDetailed?  = null
    private var serieDetailed: SerieDetailed? = null
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
//                viewModelCast = ViewModelProvider(this).get(MovieCreditsViewModel::class.java)
//                viewModelCast.getCredits(films?.id)

                viewModelMovie = ViewModelProvider(this).get(MovieDetailedViewModel::class.java)
                viewModelMovie.getMovie(films?.id)

                viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)
                viewModel.getGenres()

                binding.btSeasonsFilmsSeries.isVisible = false
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

                viewModelMovie.movieSucess.observe(this) {
                    it?.let { movie ->
                        val horas = movie.runtime?.div(60)
                        val minutos = movie.runtime?.rem(60)
                        binding.tvTimeFilmsSeries.text = "${horas}h${minutos}min"
                        binding.tvYearFilmsSeries.text = "(${movie?.release_date?.getFirst4Chars()})"
                        binding.rvCardsListActors.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = movie?.credits.cast?.let { it1 ->
                                MovieCreditsAdapter(it1){
                                    val castClicked = it
                                    castClicked?.let{result->
                                        val intent = Intent(this@FilmsAndSeriesActivity, PeopleActivity::class.java)
                                        intent.putExtra(BASE_ACTOR_KEY, result)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }

                        movie.videos?.results?.forEach { trailer ->
                            binding.btTrailerFilmsSeries.isVisible = trailer.key != ""
                        }
                        binding.btTrailerFilmsSeries.setOnClickListener {
                            youtubeMovies(it)
                        }

                        binding.btStreamingFilmsSeries.isVisible = movie.homepage != ""
                        binding.btStreamingFilmsSeries.setOnClickListener {
                            val uri = Uri.parse(movie.watch_providers?.results?.BR?.link)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }

                        binding.rvStreaming.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = movie.watch_providers?.results?.BR?.let { it1 ->
                                MovieStreamingAdapter(it1.flatrate){
                                    val uri = Uri.parse(movie.homepage)
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    startActivity(intent)
                                }
                            }
                        }
                        filmDetailed = movie
                    }
                }
//                binding.btWebSiteFilmsSeries.setOnClickListener {
//                    val uri = Uri.parse(filmDetailed?.homepage)
//                    val intent = Intent(Intent.ACTION_VIEW, uri)
//                    startActivity(intent)
//                }

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
                viewModelSerie = ViewModelProvider(this).get(SerieDetailedViewModel::class.java)

                viewModelSerie.getSerieDetailed(series?.id)
                viewModel.getGenres()

                viewModelSerie.serieDetailedSucess.observe(this) { serie ->
                    serieDetailed = serie
                    setupObservablesSeries()
                    Glide.with(binding.root.context)
                            .load(serie?.poster_path)
                            .into(binding.ivBannerFilmsSeries)
                    serie?.backdrop_path?.let {
                        Glide.with(binding.root.context)
                                .load(serie?.backdrop_path)
                                .into(binding.ivBackDropFilmSeries)
                    }

                    binding.tvDescriptionTextFilmsSeries.text = serie?.overview
                    binding.tvNameFilmsSeries.text = serie?.name
                    binding.tvNoteFilmsSeries.text = "${(serie?.vote_average)?.div(2)}"
                    serie?.vote_average?.let {
                        binding.ratingBarFilmsSeries.rating = (it / 2.0f).toFloat()
                        binding.ratingBarFilmsSeries.stepSize = .5f
                    }
                    binding.tvYearFilmsSeries.text = "(${serie?.first_air_date?.getFirst4Chars()})"

                    binding.btStreamingFilmsSeries.isVisible = serie.homepage != ""
                    binding.btStreamingFilmsSeries.setOnClickListener {
                        val uri = Uri.parse(serie.watch_providers?.results?.BR?.link)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }

                    binding.rvStreaming?.apply {
                        layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = serie?.watch_providers?.results?.BR?.flatrate?.let {
                            SerieStreamingAdapter(it) {
                                val uri = Uri.parse(serie.homepage)
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        }
                    }
                    serie?.episode_run_time?.forEach { binding.tvTimeFilmsSeries.text = "$it min" }
                    serie?.videos?.results?.forEach {
                        binding.btTrailerFilmsSeries.isVisible = it.key != ""
                    }
                    binding.btTrailerFilmsSeries.setOnClickListener {
                        youtubeSeries(it)
                    }

                    serie?.credits?.cast.let { castSerie ->
                        binding.rvCardsListActors.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = castSerie?.let { it1 -> SerieCastAdapter(it1) }
                        }
                    }

                    binding.btSeasonsFilmsSeries.setOnClickListener {
                        val intent = Intent(this, SeasonsActivity::class.java)
                        intent.putExtra("season", serie)
                        startActivity(intent)
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
        viewModel.onResultGenres.observe(this) {
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
        }
    }

    private fun setupObservablesSeries() {
        var generosText = ""
        viewModel.onResultGenres.observe(this) {
            it?.let { generos ->
                series?.genreIds?.forEach { genreSeries ->
                    generos.genres.forEach { genre ->
                        if (genre.id == genreSeries) {
                            generosText += "${genre.name}  "
                        }
                    }
                }
            }
            binding.tvGenderFilmsSeries.text = generosText
        }
    }

    private fun youtubeMovies(click: View) {
        val dialog = Dialog(click.context)
        dialog.setContentView(R.layout.youtube_popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val iFramePlayerOptions = IFramePlayerOptions.Builder().controls(1).build()
        var key = ""
        filmDetailed?.videos?.results?.forEach {
            key = it.key
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
        var key: String? = null
        serieDetailed?.videos?.results?.forEach { trailer ->
            key = trailer.key
        }
        lifecycle.addObserver(dialog.youtubePlayerDialog)
        dialog.youtubePlayerDialog.initialize(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                key?.let { youTubePlayer.loadOrCueVideo(lifecycle, it, 0.0f) }
            }
        }, true, iFramePlayerOptions)

        dialog.show()
    }
}
