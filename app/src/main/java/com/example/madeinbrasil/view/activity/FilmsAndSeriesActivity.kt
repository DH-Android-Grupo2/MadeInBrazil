package com.example.madeinbrasil.view.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.MovieCreditsAdapter
import com.example.madeinbrasil.adapter.SerieCastAdapter
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.extensions.getFirst4Chars
import com.example.madeinbrasil.model.home.CommentRepository
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result
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

class FilmsAndSeriesActivity : AppCompatActivity() {

    private lateinit var viewModel: GenderMovieViewModel
    private lateinit var viewModelTrailer: TrailerViewModel
    private lateinit var viewModelTrailerSeries: TrailerSeriesViewModel
    private lateinit var viewModelCastSerie: SerieCreditsViewModel
    private lateinit var viewModelCast: MovieCreditsViewModel
    private lateinit var binding: ActivityFilmsAndSeriesBinding

    private var films: Result? = null
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

        Log.i("position", positionFragment.toString())
        binding.ivArrowBackFilmsSeries.setOnClickListener {
            finish()
        }

        when(positionFragment) {
            1 -> {
                viewModelCast = ViewModelProvider(this).get(MovieCreditsViewModel::class.java)
                viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)
                viewModelTrailer = ViewModelProvider(this).get(TrailerViewModel::class.java)
                viewModelCast.getCredits(films?.id)
                viewModelTrailer.getTrailer(films?.id)

                viewModel.getGenres()

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
                binding.tvNoteFilmsSeries.text = "${films?.voteAverage?.div(2)}"
                films?.voteAverage?.let {
                    binding.ratingBarFilmsSeries.rating = it/2.0f
                    binding.ratingBarFilmsSeries.stepSize = .5f
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

                viewModelCast.onResultCredits?.observe(this) {
                    it?.cast.let { cast ->
                        binding.rvCardsListActors.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = cast?.let { it1 -> MovieCreditsAdapter(it1) }
                        }
                    }
                }

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
                    series?.backdropPath?.let{
                        Glide.with(binding.root.context)
                                .load(series?.backdropPath)
                                .into(binding.ivBackDropFilmSeries)
                    }

                    binding.tvDescriptionTextFilmsSeries.text = series?.overview
                    binding.tvNameFilmsSeries.text = series?.name
                    binding.tvNoteFilmsSeries.text = "${(series?.voteAverage)?.div(2)}"
                    series?.voteAverage?.let {
                        binding.ratingBarFilmsSeries.rating = (it/2.0f).toFloat()
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
