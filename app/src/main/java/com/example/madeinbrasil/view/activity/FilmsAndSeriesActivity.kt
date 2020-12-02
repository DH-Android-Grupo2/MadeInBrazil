package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.adapter.MainAdapterComments
import com.example.madeinbrasil.viewModel.SerieCreditsViewModel
import com.example.madeinbrasil.viewModel.TrailerViewModel
import com.example.madeinbrasil.viewmodel.GenderMovieViewModel
import com.example.madeinbrasil.viewmodel.MovieCreditsViewModel

class FilmsAndSeriesActivity : AppCompatActivity() {

    private lateinit var viewModel: GenderMovieViewModel
    private lateinit var viewModelTrailer: TrailerViewModel
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
                viewModelCast.getCredits(films?.id)
                viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)
                viewModelTrailer = ViewModelProvider(this).get(TrailerViewModel::class.java)

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
                binding.tvNoteFilmsSeries.text = "${films?.voteAverage?.div(2)}"
                films?.voteAverage?.let {
                    binding.ratingBarFilmsSeries.rating = it/2.0f
                    binding.ratingBarFilmsSeries.stepSize = .5f
                }

                binding.btTrailerFilmsSeries.setOnClickListener {
                    val test = films?.id?.let { viewModelTrailer.getTrailer(it) }
                    Log.i("test", test.toString())
                    Log.i("test", "cliquei")

//                    fun openYoutubeLink (youtubeID: String) {
//                        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("$BASE_URL_YOUTUBE_APP$youtubeID"))
//                        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("$BASE_URL_YOUTUBE_BROWSER$youtubeID"))
//                        try {
//                            this.startActivity(intentApp)
//                        } catch (ex: ActivityNotFoundException) {
//                            this.startActivity(intentBrowser)
//                        }
//                    }
                }

                binding.tvYearFilmsSeries.text = "${films?.releaseDate?.getFirst4Chars()}"

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
                viewModelCastSerie.getCreditsSerie(series?.id)
                viewModel.getGenres()
                //setupObservables()

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



}
