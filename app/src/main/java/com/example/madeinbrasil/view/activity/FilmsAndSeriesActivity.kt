package com.example.madeinbrasil.view.activity


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.*
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.extensions.getFirst4Chars
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.cast.MidiaCastCrossRef
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.database.entities.season.SeasonFirebase
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.home.CommentRepository
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.serieDetailed.Genre
import com.example.madeinbrasil.model.serieDetailed.Season
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_ACTOR_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_MIDIA_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SEASON_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SEASON_KEY_OFF
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.VALUE
import com.example.madeinbrasil.view.adapter.MainAdapterComments
import com.example.madeinbrasil.viewModel.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import kotlinx.android.synthetic.main.youtube_popup.*
import com.example.madeinbrasil.viewModel.GenderMovieViewModel
import com.example.madeinbrasil.viewModel.MovieDetailedViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.choose_list_popup.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class FilmsAndSeriesActivity : AppCompatActivity() {

    private lateinit var viewModel: GenderMovieViewModel
    private lateinit var viewModelGenderSeries: GenderSerieViewModel
    private lateinit var viewModelMovie: MovieDetailedViewModel
    private lateinit var viewModelSerie: SerieDetailedViewModel
    private lateinit var viewModelObjects: FilmsAndSerieViewModel
    private lateinit var customListViewModel: CustomListViewModel
    private lateinit var binding: ActivityFilmsAndSeriesBinding

    private var films: Result? = null
    private var filmDetailed: MovieDetailed?  = null
    private var serieDetailed: SerieDetailed? = null
    private var series: ResultSearch? = null
    private var midiaFirebase: MidiaFirebase? = null
    private var comments = CommentRepository().setComments()
    private var positionFragment = 0
    private val handleListCast = mutableListOf<CastFirebase>()
    private val handleListMidia = mutableListOf<MidiaFirebase>()
    private val handleListSeason = mutableListOf<SeasonFirebase>()

    companion object {
        var CAST: MutableList<CastFirebase> = mutableListOf()
        var SEASON: MutableList<SeasonFirebase> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmsAndSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        films = intent.getParcelableExtra(BASE_FILM_KEY)
        series = intent.getParcelableExtra(BASE_SERIE_KEY)
        midiaFirebase = intent.getParcelableExtra(BASE_MIDIA_KEY)
        positionFragment = intent.getIntExtra(ID_FRAGMENTS, 0)
        viewModelObjects = ViewModelProvider(this).get(FilmsAndSerieViewModel::class.java)

        binding.ivArrowBackFilmsSeries.setOnClickListener {
            finish()
        }

//        startObjects(films?.id, series?.id)
//        tutorialImplementation()

        when (positionFragment) {
            1 -> {
                viewModelMovie = ViewModelProvider(this).get(MovieDetailedViewModel::class.java)
                films?.let {
                    viewModelMovie.getMovie(it.id)
                }?: run {
                    viewModelMovie.getMovie(midiaFirebase?.id)
                }

                viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)
                viewModel.getGenres()
                customListViewModel = ViewModelProvider(this).get(CustomListViewModel::class.java)
                binding.btSeasonsFilmsSeries.isVisible = false
                setupObservables()

                viewModelMovie.movieSucess.observe(this) {
                    it?.let { movie ->
                        val horas = movie.runtime?.div(60)
                        val minutos = movie.runtime?.rem(60)
                        binding.tvTimeFilmsSeries.text = "${horas}h${minutos}min"
                        binding.tvYearFilmsSeries.text = "(${movie?.release_date?.getFirst4Chars()})"

                        if(movie.credits.cast?.size != 0) {
                            binding.rvCardsListActors.apply {
                                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                                adapter = movie?.credits.cast?.let { it1 ->
                                    MovieCreditsAdapter(it1) {
                                        val castClicked = it
                                        castClicked?.let { result ->
                                            val intent = Intent(this@FilmsAndSeriesActivity, PeopleActivity::class.java)
                                            intent.putExtra(BASE_ACTOR_KEY, result)
                                            intent.putExtra(VALUE, 1)
                                            startActivity(intent)
                                        }
                                    }
                                }
                            }
                            binding.tvMessageCast.isVisible = false
                        }

                        // ADDED 21/01
                        binding.cbListFilmsSeries.setOnClickListener {
                            val dialog = Dialog(this)
                            dialog.setContentView(R.layout.choose_list_popup)
                            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            customListViewModel.getCustomLists()

                            customListViewModel.customLists.observe(this){ list ->
                                if(list.isEmpty())
                                    dialog.tvEmptyList.visibility = View.VISIBLE
                                else
                                    dialog.rvCustomLists.apply {
                                        layoutManager = GridLayoutManager(this@FilmsAndSeriesActivity, 1)
                                        adapter = ChooseListAdapter(list) {

                                        }
                                    }
                            }
                            dialog.show()
                        }

                        Glide.with(this)
                                .load(movie.poster_path)
                                .placeholder(R.drawable.logo_made_in_brasil)
                                .into(binding.ivBannerFilmsSeries)
                        movie.backdrop_path?.let {
                            Glide.with(this)
                                    .load(it)
                                    .placeholder(R.drawable.logo_made_in_brasil)
                                    .into(binding.ivBackDropFilmSeries)
                        } ?: Glide.with(this)
                                .load(R.drawable.logo_made_in_brasil)
                                .into(binding.ivBackDropFilmSeries)

                        binding.tvDescriptionTextFilmsSeries.text = movie.overview
                        binding.tvNameFilmsSeries.text = movie.title
                        binding.tvNoteFilmsSeries.text = "${movie.vote_average?.div(2)}"
                        movie.vote_average?.let {
                            binding.ratingBarFilmsSeries.rating = (it / 2.0f).toFloat()
                            binding.ratingBarFilmsSeries.stepSize = .5f
                        }

                        movie.videos?.results?.forEach { trailer ->
                            binding.btTrailerFilmsSeries.isVisible = trailer.key != ""
                        }
                        binding.btTrailerFilmsSeries.setOnClickListener {
                            youtubeMovies(it)
                        }

                        binding.btStreamingFilmsSeries.isVisible = movie.watch_providers?.results?.BR?.link != null
                        binding.btStreamingFilmsSeries.setOnClickListener {
                            val uri = Uri.parse(movie.watch_providers?.results?.BR?.link)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }

                        if (movie.recommendations?.results?.size != 0) {
                            binding.rvCardsListRecomendacoes.apply {
                                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                                adapter = movie.recommendations?.let { it1 ->
                                    Log.i("lLLLLO", "$it1")
                                    it1.results?.let { it2 ->
                                        FilmsSeriesFromUserAdapter(it2) { result ->
                                            val intent = Intent(this@FilmsAndSeriesActivity, FilmsAndSeriesActivity::class.java)
                                            intent.putExtra(BASE_FILM_KEY, result)
                                            intent.putExtra(ID_FRAGMENTS, 1)
                                            startActivity(intent)
                                        }
                                    }
                                }
                            }
                            binding.tvMessageRecomendation.isVisible = false
                        }

                        if (movie.similar?.results?.size != 0) {
                            binding.rvCardsListSimilares.apply {
                                layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                                adapter = movie.similar?.let { it1 ->
                                    it1.results?.let { it2 ->
                                        FilmsSeriesFromUserAdapter(it2) { result ->
                                            val intent = Intent(this@FilmsAndSeriesActivity, FilmsAndSeriesActivity::class.java)
                                            intent.putExtra(BASE_FILM_KEY, result)
                                            intent.putExtra(ID_FRAGMENTS, 1)
                                            startActivity(intent)
                                        }
                                    }
                                }
                            }
                            binding.tvMessageSimilar.isVisible = false
                        }

                        //binding.rvStreaming.isVisible = movie.homepage != ""
                        binding.rvStreaming.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = movie.watch_providers?.results?.BR?.let { it1 ->
                                it1.flatrate?.let { it2 ->
                                    MovieStreamingAdapter(it2) {
                                        if (movie.homepage != "") {
                                            val uri = Uri.parse(movie.homepage)
                                            val intent = Intent(Intent.ACTION_VIEW, uri)
                                            startActivity(intent)
                                        }
                                    }
                                }
                            }
                        }
                        filmDetailed = movie

                        if(MenuActivity.USER.favorites.contains(movie.id)) {
                            binding.cbFavoriteFilmsSeries.isChecked = true
                        }
                        if(MenuActivity.USER.watched.contains(movie.id)) {
                            binding.cbWatchedFilmsSeries.isChecked = true
                        }

                        binding.cbFavoriteFilmsSeries.setOnCheckedChangeListener { _, isChecked ->
                            if(isChecked) {
                                MenuActivity.USER.favorites.add(movie.id)
                            }else {
                                MenuActivity.USER.favorites.remove(movie.id)
                            }
                            viewModelMovie.updateUser(MenuActivity.USER)
                        }

                        binding.cbWatchedFilmsSeries.setOnCheckedChangeListener { _, isChecked ->
                            if(isChecked) {
                                MenuActivity.USER.watched.add(movie.id)
                            }else {
                                MenuActivity.USER.watched.remove(movie.id)
                            }
                            viewModelMovie.updateUser(MenuActivity.USER)
                        }

                        MenuActivity.MIDIA
                        val hasMidia = MenuActivity.MIDIA.filter { it.id == movie.id }
                        if(hasMidia.isNotEmpty()) {
                            hasMidia.forEach {
                                if(it.origin == 2) {
                                    setMidiaFireBase(movie, null)
                                }else {
                                    movie.credits?.cast?.forEach { cast ->
                                        castSetUp(cast, null)
                                    }
                                    movie.recommendations?.results?.forEach {
                                        recommendationsAndSimilarSetUp(it, null)
                                    }
                                    movie.similar?.results?.forEach {
                                        recommendationsAndSimilarSetUp(it, null)
                                    }

                                    viewModelMovie.cast.observe(this) { cast ->
                                        setCastAndSeason(cast, 3)
                                    }
                                    viewModelMovie.midia.observe(this) {midia ->
                                        setRecommendationsAndSimilar(midia, 1)
                                    }
                                }
                            }
                        }else {
                            viewModelMovie.getMidiaFireBase(movie.id)
                            viewModelMovie.midia.observe(this) {midia ->
                                midia?.forEach {doc ->
                                    doc?.let {
                                        MenuActivity.MIDIA.add(it)
                                        movie.credits.cast?.forEach { cast ->
                                            castSetUp(cast, null)
                                        }
                                        viewModelMovie.cast.observe(this) { cast ->
                                            setCastAndSeason(cast, 3)
                                        }
                                    }?: run {
                                        setMidiaFireBase(movie, null)
                                    }
                                }
                            }
                        }
                    }
                }

                viewModelMovie.movieError.observe(this) {midia ->
                    val film = midia.filter { it.id == films?.id }
                    if(film.isNotEmpty()) {
                        onApiErrorMovie(film)
                    }
                    Snackbar.make(binding.ivArrowBackFilmsSeries, R.string.string_you_are_offline, Snackbar.LENGTH_INDEFINITE)
                            .setAction("Entendido") {
                                it.isVisible = false
                            }.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                            .setBackgroundTint(ContextCompat.getColor(this, R.color.colorAccent))
                            .setTextColor(ContextCompat.getColor(this, R.color.colorPrimary)).show()
                }

                findViewById<RecyclerView>(R.id.rvCommentsUsers).apply {
                    layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity)
                    adapter = MainAdapterComments(comments)
                }
            }

            2 -> {
                viewModelGenderSeries = ViewModelProvider(this).get(GenderSerieViewModel::class.java)
                viewModelGenderSeries.getGenres()

                viewModel = ViewModelProvider(this).get(GenderMovieViewModel::class.java)
                viewModelSerie = ViewModelProvider(this).get(SerieDetailedViewModel::class.java)

                series?.let {
                    viewModelSerie.getSerieDetailed(it?.id)
                }?: run {
                    viewModelSerie.getSerieDetailed(midiaFirebase?.id)
                }

                viewModel.getGenres()
                customListViewModel = ViewModelProvider(this).get(CustomListViewModel::class.java)
                viewModelSerie.serieDetailedSucess.observe(this) { serie ->
                    serieDetailed = serie
                    setupObservablesSeries()
                    Glide.with(binding.root.context)
                            .load(serie?.posterPath)
                            .placeholder(R.drawable.logo_made_in_brasil)
                            .into(binding.ivBannerFilmsSeries)
                    serie?.backdropPath?.let {
                        Glide.with(binding.root.context)
                                .load(serie.backdropPath)
                                .placeholder(R.drawable.logo_made_in_brasil)
                                .into(binding.ivBackDropFilmSeries)
                    } ?: Glide.with(this)
                            .load(R.drawable.logo_made_in_brasil)
                            .into(binding.ivBackDropFilmSeries)

                    binding.tvDescriptionTextFilmsSeries.text = serie?.overview
                    binding.tvNameFilmsSeries.text = serie?.name
                    binding.tvNoteFilmsSeries.text = "${(serie?.voteAverage)?.div(2)}"
                    serie?.voteAverage?.let {
                        binding.ratingBarFilmsSeries.rating = (it / 2.0f).toFloat()
                        binding.ratingBarFilmsSeries.stepSize = .5f
                    }
                    binding.tvYearFilmsSeries.text = "(${serie?.firstAirDate?.getFirst4Chars()})"

                    binding.btStreamingFilmsSeries.isVisible = serie.watchProviders?.results?.BR?.link != null
                    binding.btStreamingFilmsSeries.setOnClickListener {
                        val uri = Uri.parse(serie.watchProviders?.results?.BR?.link)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }

                    binding.rvStreaming.isVisible = serie.homepage != ""
                    binding.rvStreaming.apply {
                        layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = serie?.watchProviders?.results?.BR?.flatrate?.let {
                            SerieStreamingAdapter(it) {
                                val uri = Uri.parse(serie.homepage)
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        }
                    }
                    serie?.episodeRunTime?.forEach {
                        if (it > 60) {
                            val horas = listOf(it.div(60))
                            val min = listOf(it.rem(60))
                            binding.tvTimeFilmsSeries.text = "${horas[0]}h${min[0]}min"
                        } else {
                            binding.tvTimeFilmsSeries.text = "$it min"
                        }
                    }
                    serie?.videos?.results?.forEach {
                        binding.btTrailerFilmsSeries.isVisible = it.key != ""
                    }
                    binding.btTrailerFilmsSeries.setOnClickListener {
                        youtubeSeries(it)
                    }

                    serie?.credits?.cast.let { castSerie ->
                        binding.rvCardsListActors.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = castSerie?.let { it1 ->
                                SerieCastAdapter(it1) {
                                    val intent = Intent(this@FilmsAndSeriesActivity, PeopleActivity::class.java)
                                    intent.putExtra(BASE_ACTOR_KEY, it)
                                    intent.putExtra(VALUE, 2)
                                    startActivity(intent)
                                }
                            }
                        }
                        binding.tvMessageCast.isVisible = false
                    }

                    // ADDED 21/01
                    binding.cbListFilmsSeries.setOnClickListener {
                        val dialog = Dialog(this)
                        dialog.setContentView(R.layout.choose_list_popup)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        customListViewModel.getCustomLists()

                        customListViewModel.customLists.observe(this){ list ->
                            if(list.isEmpty())
                                dialog.tvEmptyList.visibility = View.VISIBLE
                            else
                                dialog.rvCustomLists.apply {
                                    layoutManager = GridLayoutManager(this@FilmsAndSeriesActivity, 1)
                                    adapter = ChooseListAdapter(list) {

                                    }
                                }
                        }
                        dialog.show()
                    }

                    binding.btSeasonsFilmsSeries.setOnClickListener {
                        val intent = Intent(this, SeasonsActivity::class.java)
                        intent.putExtra(SEASON_KEY, serie)
                        startActivity(intent)
                    }

                    if (serie.recommendations?.results?.size != 0) {
                        binding.rvCardsListRecomendacoes.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = serie.recommendations?.let { it1 ->
                                it1.results?.let { it2 ->
                                    CastDetailsAdapter(it2) { result ->
                                        val intent = Intent(this@FilmsAndSeriesActivity, FilmsAndSeriesActivity::class.java)
                                        intent.putExtra(BASE_SERIE_KEY, result)
                                        intent.putExtra(ID_FRAGMENTS, 2)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                        binding.tvMessageRecomendation.isVisible = false
                    }

                    if (serie.similar?.results?.size != 0) {
                        binding.rvCardsListSimilares.apply {
                            layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = serie.similar?.let { it1 ->
                                it1.results?.let { it2 ->
                                    CastDetailsAdapter(it2) { result ->
                                        val intent = Intent(this@FilmsAndSeriesActivity, FilmsAndSeriesActivity::class.java)
                                        intent.putExtra(BASE_SERIE_KEY, result)
                                        intent.putExtra(ID_FRAGMENTS, 2)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                        binding.tvMessageSimilar.isVisible = false
                    }

                    if(MenuActivity.USER.favorites.contains(serie.id)) {
                        binding.cbFavoriteFilmsSeries.isChecked = true
                    }
                    if(MenuActivity.USER.watched.contains(serie.id)) {
                        binding.cbWatchedFilmsSeries.isChecked = true
                    }

                    binding.cbFavoriteFilmsSeries.setOnCheckedChangeListener { _, isChecked ->
                        if(isChecked) {
                            MenuActivity.USER.favorites.add(serie.id)
                        }else {
                            MenuActivity.USER.favorites.remove(serie.id)
                        }
                        viewModelSerie.updateUser(MenuActivity.USER)
                    }

                    binding.cbWatchedFilmsSeries.setOnCheckedChangeListener { _, isChecked ->
                        if(isChecked) {
                            MenuActivity.USER.watched.add(serie.id)
                        }else {
                            MenuActivity.USER.watched.remove(serie.id)
                        }
                        viewModelSerie.updateUser(MenuActivity.USER)
                    }

                    val hasMidia = MenuActivity.MIDIA.filter { it.id == serie.id }
                    if(hasMidia.isNotEmpty()) {
                        hasMidia.forEach {
                            if(it.origin == 2) {
                                setMidiaFireBase(null, serie)
                            } else {
                                serie.seasons?.forEach {season ->
                                    seasonsSetUp(season)
                                }
                                serie.credits?.cast?.forEach { cast ->
                                    castSetUp(null, cast)
                                }
                                serie.recommendations?.results?.forEach {rec ->
                                    recommendationsAndSimilarSetUp(null, rec)
                                }
                                serie.similar?.results?.forEach { similar ->
                                    recommendationsAndSimilarSetUp(null, similar)
                                }

                                viewModelSerie.cast.observe(this) { cast ->
                                    setCastAndSeason(cast, 1)
                                }
                                viewModelSerie.season.observe(this) {season ->
                                    setCastAndSeason(season, 2)
                                }
                                viewModelSerie.midia.observe(this) {midia ->
                                    setRecommendationsAndSimilar(midia, 2)
                                }
                            }
                        }
                    }else {
                        viewModelSerie.getMidiaFireBase(serie.id)
                        viewModelSerie.midia.observe(this) {midia ->
                            midia?.forEach {
                                it?.let { mid ->
                                    MenuActivity.MIDIA.add(mid)
                                    serie.seasons?.forEach {season ->
                                        seasonsSetUp(season)
                                    }
                                    serie.credits?.cast?.forEach { cast ->
                                        castSetUp(null, cast)
                                    }
                                    viewModelSerie.cast.observe(this) { cast ->
                                        setCastAndSeason(cast, 1)
                                    }
                                    viewModelSerie.season.observe(this) {season ->
                                        setCastAndSeason(season, 2)
                                    }
                                }?: run {
                                    setMidiaFireBase(null, serie)
                                }
                            }
                        }
                    }
                }

                viewModelSerie.serieDetailedError.observe(this) {midia ->
                    val serie = midia.filter { it.id == series?.id }
                    if(serie.isNotEmpty()) {
                        onApiErrorSerie(serie)
                    }

                    Snackbar.make(binding.ivArrowBackFilmsSeries, R.string.string_you_are_offline, Snackbar.LENGTH_INDEFINITE)
                            .setAction("Entendido") {
                                it.isVisible = false
                            }.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                            .setBackgroundTint(ContextCompat.getColor(this, R.color.colorAccent))
                            .setTextColor(ContextCompat.getColor(this, R.color.colorPrimary)).show()
                }

                findViewById<RecyclerView>(R.id.rvCommentsUsers).apply {
                    layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity)
                    adapter = MainAdapterComments(comments)
                }
            }
        }
    }

    private fun setRecommendationsAndSimilar(element: MutableList<MidiaFirebase?>, pos: Int) {
        when(pos) {
            1 -> {
                element.forEach {doc ->
                    doc?.let {
                        if(!MenuActivity.MIDIA.contains(it)) {
                            MenuActivity.MIDIA.add(it)
                            MenuActivity.MIDIA
                        }
                    }
                }
                handleListMidia.forEach { handle ->
                    if(!MenuActivity.MIDIA.contains(handle)) {
                        viewModelMovie.setMidiaFireBase(handle.id, handle)
                    }
                }
            }
            2 -> {
                element.forEach {doc ->
                    doc?.let {
                        if(!MenuActivity.MIDIA.contains(it)) {
                            MenuActivity.MIDIA.add(it)
                            MenuActivity.MIDIA
                        }
                    }
                }
                handleListMidia.forEach { handle ->
                    if(!MenuActivity.MIDIA.contains(handle)) {
                        viewModelSerie.setMidiaFireBase(handle.id, handle)
                    }
                }
            }
        }
    }

    private fun setCastAndSeason(element: MutableList<DocumentSnapshot>?, pos: Int) {
        when(pos) {
            1 -> {
                element?.let {doc ->
                    doc.forEach {
                        val obj = it.toObject(CastFirebase::class.java)
                        obj?.let {
                            if(!CAST.contains(it)) {
                                CAST.add(it)
                            }
                        }
                    }
                    handleListCast.forEach { handle ->
                        if(!CAST.contains(handle)) {
                            viewModelSerie.setCastFireBase(handle.id, handle)
                        }
                    }
                }
            }
            2 -> {
                element?.let {
                    it.forEach {
                        val obj = it.toObject(SeasonFirebase::class.java)
                        obj?.let {
                            if(!SEASON.contains(it)) {
                                SEASON.add(it)
                            }
                        }
                    }
                    handleListSeason.forEach { handle ->
                        if(!SEASON.contains(handle)) {
                            viewModelSerie.setSeasonFireBase(handle.id, handle)
                        }
                    }
                }
            }
            3 -> {
                element?.let {doc ->
                    doc.forEach {
                        val obj = it.toObject(CastFirebase::class.java)
                        obj?.let {
                            if(!CAST.contains(it)) {
                                CAST.add(it)
                            }
                        }
                    }
                    handleListCast.forEach { handle ->
                        if(!CAST.contains(handle)) {
                            viewModelMovie.setCastFireBase(handle.id, handle)
                        }
                    }
                }
            }
        }
    }

    private fun setMidiaFireBase(movieDetailed: MovieDetailed?, serieDetailed: SerieDetailed?) {
        val listCast = mutableListOf<Int>()
        val listRecommendations = mutableListOf<Int>()
        val listSimilar = mutableListOf<Int>()
        val listGender = mutableListOf<Int>()
        val listSeasons = mutableListOf<Int>()
        var hours: Int?
        var minutes: Int?

        movieDetailed?.let {movie ->
            hours = movie.runtime?.div(60)
            minutes = movie.runtime?.rem(60)

            movie.credits.cast?.forEach {cast ->
                listCast.add(cast.id)
                castSetUp(cast, null)
            }
            movie.genres?.forEach {gender ->
                listGender.add(gender.id)
                gendersSetUp(gender, null)
            }
            movie.recommendations?.results?.forEach {rec ->
                listRecommendations.add(rec.id)
                val indice = MenuActivity.MIDIA.filter { it.id == rec.id }
                if(indice.isEmpty()) {
                    recommendationsAndSimilarSetUp(rec, null)
                }
            }
            movie.similar?.results?.forEach {similar ->
                listSimilar.add(similar.id)
                val indice = MenuActivity.MIDIA.filter { it.id == similar.id }
                if(indice.isEmpty()) {
                    recommendationsAndSimilarSetUp(similar, null)
                }
            }

            val midia = MidiaFirebase(
                    movie.id, movie.backdrop_path, listCast, "${hours}h${minutes}min", movie.release_date?.getFirst4Chars(),
                    listGender, movie.homepage, movie.title, movie.overview, movie.poster_path, listRecommendations,null,
                    listSimilar, movie.vote_average, 1, 1
            )
            val getMidia = MenuActivity.MIDIA.filter { it.id == midia.id }
            getMidia.forEach {
                MenuActivity.MIDIA.remove(it)
                MenuActivity.MIDIA.add(midia)
            }
            if(!MenuActivity.MIDIA.contains(midia)) {
                MenuActivity.MIDIA.add(midia)
            }

            viewModelMovie.setMidiaFireBase(midia.id, midia)
        }
        serieDetailed?.let {serie ->
            var time: String? = ""
            serie.episodeRunTime?.forEach {
                if(it > 60) {
                    hours = it.div(60)
                    minutes = it.rem(60)
                    time = "${hours}h${minutes}min"
                }else {
                    time = "${it}min"
                }
            }
            serie.seasons?.forEach {season ->
                listSeasons.add(season.id)
                seasonsSetUp(season)
            }

            serie.credits?.cast?.forEach{
                castSetUp(null, it)
                listCast.add(it.id)
            }
            serie.genres?.forEach {gender ->
                listGender.add(gender.id)
                gendersSetUp(null, gender)
            }
            serie.recommendations?.results?.forEach {rec ->
                listRecommendations.add(rec.id)
                val indice = MenuActivity.MIDIA.filter { it.id == rec.id }
                if(indice.isEmpty()) {
                    recommendationsAndSimilarSetUp(null, rec)
                }
            }
            serie.similar?.results?.forEach {similar ->
                listSimilar.add(similar.id)
                val indice = MenuActivity.MIDIA.filter { it.id == similar.id }
                if(indice.isEmpty()) {
                    recommendationsAndSimilarSetUp(null, similar)
                }
            }

            val midia = MidiaFirebase(
                    serie.id, serie.backdropPath, listCast, time, serie.firstAirDate?.getFirst4Chars(),
                    listGender, serie.homepage, serie.name, serie.overview, serie.posterPath, listRecommendations, listSeasons,
                    listSimilar, serie.voteAverage, 1, 2
            )
            val getMidia = MenuActivity.MIDIA.filter { it.id == midia.id }
            getMidia.forEach {
                MenuActivity.MIDIA.remove(it)
                MenuActivity.MIDIA.add(midia)
            }
            if(!MenuActivity.MIDIA.contains(midia)) {
                MenuActivity.MIDIA.add(midia)
            }

            viewModelSerie.setMidiaFireBase(serie.id, midia)
        }
    }

    private fun recommendationsAndSimilarSetUp(rec: Result?, recSerie: ResultSearch?) {
        val listGender = mutableListOf<Int>()

        rec?.let {
            rec.genreIds.forEach {
                listGender.add(it)
            }

            val midia = MidiaFirebase(
                    rec.id, rec.backdropPath?.getFullImagePath(), null, null, rec.releaseDate?.getFirst4Chars(),
                    listGender, null, rec.title, rec.overview, rec.posterPath, null,null,
                    null, rec.voteAverage?.toDouble(), 2, 1
            )
            if(!MenuActivity.MIDIA.contains(midia)) {
                viewModelMovie.getMidiaFireBase(midia.id)
                handleListMidia.add(midia)
            }
        }
        recSerie?.let {
            recSerie.genreIds.forEach {
                listGender.add(it)
            }

            val midia = MidiaFirebase(
                    recSerie.id, recSerie.backdropPath?.getFullImagePath(), null, null, recSerie.releaseDate?.getFirst4Chars(),
                    listGender, null, recSerie.name, recSerie.overview, recSerie.posterPath, null,null,
                    null, recSerie.voteAverage, 2, 2
            )
            if(!MenuActivity.MIDIA.contains(midia)) {
                viewModelSerie.getMidiaFireBase(midia.id)
                handleListMidia.add(midia)
            }
        }
    }

    private fun gendersSetUp(genreMovie: com.example.madeinbrasil.model.result.Genre?, genreSerie: Genre?) {
        genreMovie?.let {
            val genre = GenreFirebase(it.id, it.name)
            if(!MenuActivity.GENRE.contains(genre)) {
                MenuActivity.GENRE.add(genre)
                viewModelMovie.setGenreFireBase(genre.id, genre)
            }
        }

        genreSerie?.let {
            when(it.id) {
                10766 -> {it.name = "Novelas"}
                10765 -> {it.name = "Ficção"}
                10767 -> {it.name = "TalkShow"}
                10768 -> {it.name = "Política"}
                10759 -> {it.name = "Ação e Aventura"}
            }
            val genre = GenreFirebase(it.id, it.name)
            if(!MenuActivity.GENRE.contains(genre)) {
                MenuActivity.GENRE.add(genre)
                viewModelSerie.setGenreFireBase(genre.id, genre)
            }
        }
    }

    private fun castSetUp(castMovie: Cast?, castSerie: com.example.madeinbrasil.model.serieDetailed.Cast?) {
        castMovie?.let {
            val casts = CastFirebase(it.id, it.name, it.profilePath)
            if(!CAST.contains(casts)) {
                viewModelMovie.getCast(it.id)
                handleListCast.add(casts)
            }
        }

        castSerie?.let {
            val casts = CastFirebase(it.id, it.name, it.profilePath)
            if(!CAST.contains(casts)) {
                viewModelSerie.getCast(it.id)
                handleListCast.add(casts)
            }
        }
    }

    private fun seasonsSetUp(season: Season?) {
        season?.let {
            val seasons = SeasonFirebase(it.air_date, it.episode_count, it.id, it.name, it.overview, it.posterPath, it.season_number)
            if(!SEASON.contains(seasons)) {
                viewModelSerie.getSeason(it.id)
                handleListSeason.add(seasons)
            }
//            if(!MenuActivity.SEASON.contains(season)) {
//                MenuActivity.SEASON.add(season)
//                viewModelSerie.setSeasonFireBase(season.id, season)
//            }
//            MenuActivity.SEASON.forEach {sea ->
//                viewModelSerie.setSeasonFireBase(sea.id, sea)
//            }
//            viewModelSerie.setSeasonFireBase(season.id, season)
        }
    }

    private fun onApiErrorSerie(serie: List<MidiaFirebase>?) {
        serie?.let {
            serie.forEach {
                if (it.id == series?.id) {
                    Glide.with(binding.root.context)
                            .load(it.posterPath)
                            .placeholder(R.drawable.logo_made_in_brasil)
                            .into(binding.ivBannerFilmsSeries)
                    it.backdropPath?.let { it1 ->
                        Glide.with(binding.root.context)
                                .load(it.backdropPath)
                                .placeholder(R.drawable.logo_made_in_brasil)
                                .into(binding.ivBackDropFilmSeries)
                    } ?: Glide.with(this)
                            .load(R.drawable.logo_made_in_brasil)
                            .into(binding.ivBackDropFilmSeries)

                    binding.tvTimeFilmsSeries.text = it.episodeRunTime
                    binding.tvDescriptionTextFilmsSeries.text = it.overview
                    binding.tvNameFilmsSeries.text = it.name
                    binding.tvNoteFilmsSeries.text = "${(it.voteAverage)?.div(2)}"
                    it.voteAverage?.let {
                        binding.ratingBarFilmsSeries.rating = (it / 2.0f).toFloat()
                        binding.ratingBarFilmsSeries.stepSize = .5f
                    }
                    binding.tvYearFilmsSeries.text = it.firstAirDate
                    binding.tvGenderFilmsSeries.text = getGendersNames(it.id)
                    binding.cbFavoriteFilmsSeries.isChecked = confirmFavorite(it.id)
                    binding.cbWatchedFilmsSeries.isChecked = confirmFavorite(it.id)
                    binding.btStreamingFilmsSeries.isVisible = false

                    binding.rvCardsListActors.apply {
                        CAST
                        val castList = mutableListOf<CastFirebase>()
                        layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                        it.cast?.forEach { cast ->
                            val filter = CAST.filter { it.id == cast }
                            if(filter.isNotEmpty()) {
                                filter.forEach { fil -> castList.add(fil) }
                            }
                        }
                        if(castList.size != 0) {
                            adapter = MidiaCastAdapter(castList)
                            binding.tvMessageCast.isVisible = false
                        }
                    }

                    binding.rvCardsListRecomendacoes.apply {
                        val recList = mutableListOf<MidiaFirebase>()
                        layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                        it.recommendations?.forEach {rec ->
                            val filter = MenuActivity.MIDIA.filter { it.id == rec }
                            if(filter.isNotEmpty()) {
                                filter.forEach {fil ->
                                    recList.add(fil)
                                }
                            }
                        }
                        if(recList.size != 0) {
                            adapter = RecommendationDataBaseAdapter(recList)
                            binding.tvMessageRecomendation.isVisible = false
                        }
                    }

                    binding.rvCardsListSimilares.apply {
                        MenuActivity.MIDIA
                        val simiList = mutableListOf<MidiaFirebase>()
                        layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                        it.similar?.forEach { simi ->
                            val filter = MenuActivity.MIDIA.filter { it.id == simi }
                            if(filter.isNotEmpty()){
                                filter.forEach { fil-> simiList.add(fil) }
                            }
                        }
                        if(simiList.size != 0) {
                            adapter = SimilarDataBaseAdapter(simiList)
                            binding.tvMessageSimilar.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun onApiErrorMovie(movie: List<MidiaFirebase>?) {
        movie?.forEach {
            if (it.id == films?.id) {
                Glide.with(binding.root.context)
                        .load(it.posterPath)
                        .placeholder(R.drawable.logo_made_in_brasil)
                        .into(binding.ivBannerFilmsSeries)
                it.backdropPath?.let { it1 ->
                    Glide.with(binding.root.context)
                            .load(it.backdropPath)
                            .placeholder(R.drawable.logo_made_in_brasil)
                            .into(binding.ivBackDropFilmSeries)
                } ?: Glide.with(this)
                        .load(R.drawable.logo_made_in_brasil)
                        .into(binding.ivBackDropFilmSeries)

                binding.tvDescriptionTextFilmsSeries.text = it.overview
                binding.tvNameFilmsSeries.text = it.name
                binding.tvNoteFilmsSeries.text = "${(it.voteAverage)?.div(2)}"
                it.voteAverage?.let {
                    binding.ratingBarFilmsSeries.rating = (it / 2.0f).toFloat()
                    binding.ratingBarFilmsSeries.stepSize = .5f
                }
                binding.tvTimeFilmsSeries.text = it.episodeRunTime
                binding.tvYearFilmsSeries.text = it.firstAirDate
                binding.tvGenderFilmsSeries.text = getGendersNames(it.id)
                binding.cbFavoriteFilmsSeries.isChecked = confirmFavorite(it.id)
                binding.cbWatchedFilmsSeries.isChecked = confirmWatched(it.id)
                binding.btStreamingFilmsSeries.isVisible = false

                binding.rvCardsListActors.apply {
                    val castList = mutableListOf<CastFirebase>()
                    layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                    it.cast?.forEach { cast ->
                        val filter = CAST.filter { it.id == cast }
                        if(filter.isNotEmpty()) {
                            filter.forEach { fil -> castList.add(fil) }
                        }
                    }
                    if(castList.size != 0) {
                        adapter = MidiaCastAdapter(castList)
                        binding.tvMessageCast.isVisible = false
                    }
                }

                binding.rvCardsListRecomendacoes.apply {
                    val recList = mutableListOf<MidiaFirebase>()
                    layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                    it.recommendations?.forEach {rec ->
                        val filter = MenuActivity.MIDIA.filter { it.id == rec }
                        if(filter.isNotEmpty()) {
                            filter.forEach {fil ->
                                recList.add(fil)
                            }
                        }
                    }
                    if(recList.size != 0) {
                        adapter = RecommendationDataBaseAdapter(recList)
                        binding.tvMessageRecomendation.isVisible = false
                    }
                }

                binding.rvCardsListSimilares.apply {
                    MenuActivity.MIDIA
                    val simiList = mutableListOf<MidiaFirebase>()
                    layoutManager = LinearLayoutManager(this@FilmsAndSeriesActivity, LinearLayoutManager.HORIZONTAL, false)
                    it.similar?.forEach { simi ->
                        val filter = MenuActivity.MIDIA.filter { it.id == simi }
                        if(filter.isNotEmpty()){
                            filter.forEach { fil-> simiList.add(fil) }
                        }
                    }
                    if(simiList.size != 0) {
                        adapter = SimilarDataBaseAdapter(simiList)
                        binding.tvMessageSimilar.isVisible = false
                    }
                }
            }
        }
    }

    private fun getGendersNames(id: Int): String {
        var gender = ""
        MenuActivity.MIDIA.forEach{midia ->
            if(midia.id == id) {
                MenuActivity.GENRE.forEach {genre ->
                    midia.genres?.forEach {
                        if(it == genre.id) {
                            gender += "${genre.name} "
                        }
                    }?: run {
                        gender
                    }
                }
            }
        }
        return gender
    }

    private fun confirmFavorite(id: Int): Boolean {
        return MenuActivity.USER.favorites.contains(id)
    }

    private fun confirmWatched(id: Int): Boolean {
        return MenuActivity.USER.watched.contains(id)
    }

//    private fun tutorialImplementation() {
//        TapTargetSequence(this).targets(
//                TapTarget.forView(binding.cbWatchedFilmsSeries,
//                        getString(R.string.string_watched_tutorial_title),
//                        getString(R.string.string_watched_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(30),
//                TapTarget.forView(binding.cbFavoriteFilmsSeries,
//                        getString(R.string.string_favorite_tutorial_title),
//                        getString(R.string.string_favorite_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(30),
//                TapTarget.forView(binding.cbListFilmsSeries,
//                        getString(R.string.string_list_tutorial_title),
//                        getString(R.string.string_list_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(30),
//                TapTarget.forView(binding?.btStreamingFilmsSeries,
//                        getString(R.string.string_streamming_tutorial_title),
//                        getString(R.string.string_streamming_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(100),
//                TapTarget.forView(binding.btSeasonsFilmsSeries,
//                        getString(R.string.string_seasons_tutorial_title),
//                        getString(R.string.string_seasons_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(100),
//                TapTarget.forView(binding.rvStreaming,
//                        getString(R.string.string_stream_tutorial_title),
//                        getString(R.string.string_stream_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(100),
//                TapTarget.forView(binding.rvCardsListActors,
//                        getString(R.string.string_cast_tutorial_title),
//                        getString(R.string.string_cast_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(100),
//                TapTarget.forView(binding.ivArrowBackFilmsSeries,
//                        getString(R.string.string_back_tutotial_title),
//                        getString(R.string.string_back_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(20)
//        ).listener(object: TapTargetSequence.Listener{
//            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
//            override fun onSequenceFinish() {}
//            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
//        }).start()
//    }

    private fun setupObservables() {
        var generosText = ""
        val listId = mutableListOf<Int>()
        val listName = mutableListOf<String>()
        viewModel.onResultGenres.observe(this) {
            it?.let { generos ->
                films?.genreIds?.forEach { genreFilm ->
                    generos.genres.forEach { genre ->
                        if (genre.id == genreFilm) {
                            generosText += "${genre.name}  "
                            listId.add(genre.id)
                            listName.add(genre.name)
                        }
                    }
                }
            }
            films?.id?.let {
                it1 -> val gender = GenreEntity(listId, listName, it1)
                viewModel.insertGenre(gender)}

            binding.tvGenderFilmsSeries.text = generosText
        }
    }

    private fun setupObservablesSeries() {
        var generosText = ""
        val listId = mutableListOf<Int>()
        val listName = mutableListOf<String>()
        viewModelGenderSeries.onResultGenresSeries.observe(this) {
            it?.let { generos ->
                series?.genreIds?.forEach { genreSerie ->
                    generos.genres.forEach { genre ->
                        if (genre.id == genreSerie) {
                            generosText += "${genre.name}  "
                            listId.add(genre.id)
                            listName.add(genre.name)
                        }
                    }
                }
            }
            series?.id?.let {
                it1 -> val gender = GenreEntity(listId, listName, it1)
                viewModelGenderSeries.insertGenre(gender)}

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
