package com.example.madeinbrasil.view.fragment

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.DiscoverTvAdapter
import com.example.madeinbrasil.adapter.HomeAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.databinding.FragmentHomeBinding
import com.example.madeinbrasil.model.discover.DiscoverMovie
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.viewModel.HomeViewModel
import com.google.android.gms.cast.framework.media.MediaUtils.getImageUri
import kotlinx.android.synthetic.main.filmsseries_popup.*
import kotlinx.android.synthetic.main.main_cards_menu.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var binding: FragmentHomeBinding? = null
    var movieComplete: MovieDetailed? = null
    private  var selected: GenreSelected? = null
    private var discover: DiscoverMovie? = null
    companion object {
         var genre : GenreSelected? = null
    }
    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter ({ it: Result?, imageView: ImageView? ->
            val movieClicked = it
            movieClicked?.let{ result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)

                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imageView,"sharedImgView")
                startActivity(intent,options.toBundle())
            }
        }, {movie ->
            setLongClickDialog(movie)
        })
    }

    private val homeAdapter2 : HomeAdapter by lazy {
        HomeAdapter ({ it: Result?, imageView: ImageView? ->
            val movieClicked = it
            movieClicked?.let{ result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)

                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imageView,"sharedImgView")
                startActivity(intent,options.toBundle())
            }

        }, {movie ->
            setLongClickDialog(movie)
        })
    }

    private val homeAdapter3 : HomeAdapter by lazy {
        HomeAdapter ({ it: Result?, imageView: ImageView? ->
            val movieClicked = it
            movieClicked?.let { result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(Constants.ConstantsFilms.BASE_FILM_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)

                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imageView,"sharedImgView")
                startActivity(intent,options.toBundle())
            }
        }, {movie ->
            setLongClickDialog(movie)
        })
    }

    private val homeAdapter4 : DiscoverTvAdapter by lazy {
        DiscoverTvAdapter ({ result ->
            result?.let {
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(Constants.ConstantsFilms.BASE_SERIE_KEY, result)
                intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 2)
                startActivity(intent)
            }
        }, {serie ->
            setLongClickDialogSerie(serie)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       selected = arguments?.getParcelable<GenreSelected>("Selected")
        genre = selected




       activity?.let{
            viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
            setupRecyclerView()
        }
//        tutorialImplementation()

        val userDao = context?.let { MadeInBrazilDatabase.getDatabase(it) }?.userDao()



    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

//    private fun tutorialImplementation() {
//        TapTargetSequence(activity).targets(
//                TapTarget.forView(binding?.logoMadeInBrasil,
//                        getString(R.string.string_welcome_tutorial_title),
//                        getString(R.string.string_welcome_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(100),
//                TapTarget.forView(binding?.rvCardsListLancamentos,
//                        getString(R.string.string_cards_tutorial_title),
//                getString(R.string.string_cards_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(220)
//        ).listener(object: TapTargetSequence.Listener{
//            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
//            override fun onSequenceFinish() {}
//            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
//        }).start()
//    }

    private fun loadContentUpcoming() {
        viewModel.upcomingMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            homeAdapter2.currentList?.clear()
            homeAdapter2.submitList(pagedList, null)
            homeAdapter2.notifyDataSetChanged()
        }
    }

    private fun loadContentNowPlaying() {
        viewModel.nowPlayingMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            homeAdapter.currentList?.clear()
            homeAdapter.submitList(pagedList, null)
            homeAdapter.notifyDataSetChanged()
        }


    }

    private fun loadContentDiscoverMovie() {
        viewModel.discoverMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            homeAdapter3.currentList?.clear()
            homeAdapter3.submitList(pagedList, null)
            homeAdapter3.notifyDataSetChanged()

        }
    }

    private fun loadContentDiscoverTv() {
        viewModel.discoverTvPagedList?.observe(viewLifecycleOwner) { pagedList ->
            homeAdapter4.currentList?.clear()
            homeAdapter4.submitList(pagedList, null)
            homeAdapter4.notifyDataSetChanged()

        }
    }

    private fun setupRecyclerView() {
        binding?.rvCardsListLancamentos?.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentNowPlaying()
            adapter = homeAdapter

        }

        binding?.rvCardsListFuturosLancamentos?.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentUpcoming()

            adapter = homeAdapter2
        }

       binding?.rvCardsListMovies?.apply {
         layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
           loadContentDiscoverMovie()
           adapter = homeAdapter3
         }

        binding?.rvCardListSeries?.apply {
           layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentDiscoverTv()
            adapter = homeAdapter4
        }
    }

    private fun setLongClickDialogSerie(serie: ResultSearch?) {
        activity?.let {activity ->
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.filmsseries_popup)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            Glide.with(activity)
                .load(serie?.posterPath)
                .placeholder(R.drawable.logo_made_in_brasil)
                .into(dialog.ivDialogPoster)

            dialog.tvDialogName.text = serie?.name
            dialog.cbShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND

                    putExtra(Intent.EXTRA_TEXT, "Série: ${serie?.name} by MadeInBrasil")
                    type = "text/plain"

                    putExtra(Intent.EXTRA_STREAM, Uri.parse(serie?.name))
                    type = "image/*"

                    putExtra(
                        Intent.EXTRA_TITLE,
                        "Filme: ${serie?.name} \nShared by MadeInBrasil"
                    )
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                }
                val shareIntent = Intent.createChooser(sendIntent, "Compartilhamento de Séries")
                ContextCompat.startActivity(it.context, shareIntent, null)
            }

            dialog.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    val dbFav = MadeInBrazilDatabase.getDatabase(activity).favoriteDao()
                    serie?.let {
                        val fav = Favorites(it.id, it.id, isChecked)
                        if(isChecked) {
                            dbFav.insertFavorite(fav)
                        }else {
                            dbFav.deleteByIdFavorites(it.id)
                        }
                    }
                }
            }

            dialog.cbWatched.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    val dbWatched = MadeInBrazilDatabase.getDatabase(activity).watchedDao()
                    serie?.let {
                        val watched = Watched(it.id, it.id, isChecked)
                        if(isChecked) {
                            dbWatched.insertWatched(watched)
                        }else {
                            dbWatched.deleteByIdWatched(it.id)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                val dbFav = MadeInBrazilDatabase.getDatabase(activity).favoriteDao()
                val dbWatched = MadeInBrazilDatabase.getDatabase(activity).watchedDao()

                dbFav.getMidiaWithFavorites().forEach {
                    if(it.midia.id == serie?.id) {
                        it.favorites.forEach {
                            dialog.cbFavorite.isChecked = it.isChecked
                        }
                    }
                }

                dbWatched.getMidiaWithWatched().forEach {
                    if(it.midia.id == serie?.id) {
                        it.watched.forEach {
                            dialog.cbWatched.isChecked = it.isChecked
                        }
                    }
                }
            }

            dialog.show()
        }
    }

    private fun setLongClickDialog(movie: Result?) {
        activity?.let {activity ->
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.filmsseries_popup)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            Glide.with(activity)
                .load(movie?.posterPath)
                .placeholder(R.drawable.logo_made_in_brasil)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(dialog.ivDialogPoster)

            dialog.tvDialogName.text = movie?.title

            dialog.cbShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND

                    putExtra(Intent.EXTRA_TEXT, "Filme: ${movie?.title} by MadeInBrasil")
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TITLE, "Filme: ${movie?.title} \nShared by MadeInBrasil")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                }
                val shareIntent = Intent.createChooser(sendIntent, "null")
                ContextCompat.startActivity(it.context, shareIntent, null)
            }

            dialog.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    val dbFav = MadeInBrazilDatabase.getDatabase(activity).favoriteDao()
                    movie?.let {
                        val fav = Favorites(it.id, it.id, isChecked)
                        if(isChecked) {
                            dbFav.insertFavorite(fav)
                        }else {
                            dbFav.deleteByIdFavorites(it.id)
                        }
                    }
                }
            }

            dialog.cbWatched.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    val dbWatched = MadeInBrazilDatabase.getDatabase(activity).watchedDao()
                    movie?.let {
                        val watched = Watched(it.id, it.id, isChecked)
                        if(isChecked) {
                            dbWatched.insertWatched(watched)
                        }else {
                            dbWatched.deleteByIdWatched(it.id)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                val dbFav = MadeInBrazilDatabase.getDatabase(activity).favoriteDao()
                val dbWatched = MadeInBrazilDatabase.getDatabase(activity).watchedDao()

                dbFav.getMidiaWithFavorites().forEach {
                    if(it.midia.id == movie?.id) {
                        it.favorites.forEach {
                            dialog.cbFavorite.isChecked = it.isChecked
                        }
                    }
                }

                dbWatched.getMidiaWithWatched().forEach {
                    if(it.midia.id == movie?.id) {
                        it.watched.forEach {
                            dialog.cbWatched.isChecked = it.isChecked
                        }
                    }
                }
            }

            dialog.show()
        }
    }
}