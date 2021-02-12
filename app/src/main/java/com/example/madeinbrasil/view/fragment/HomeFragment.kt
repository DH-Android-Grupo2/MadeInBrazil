package com.example.madeinbrasil.view.fragment

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.DiscoverTvAdapter
import com.example.madeinbrasil.adapter.HomeAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.databinding.FragmentHomeBinding
import com.example.madeinbrasil.model.discover.DiscoverMovie
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_DETAILED_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.TUTORIAL
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.view.activity.MenuActivity
import com.example.madeinbrasil.viewModel.HomeViewModel
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.cast.framework.media.MediaUtils.getImageUri
import kotlinx.android.synthetic.main.filmsseries_popup.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    var movieComplete: MovieDetailed? = null
    private  var selected: GenreSelected? = null
    private var tutorial: Int? = null
    private var discover: DiscoverMovie? = null
    private val takeShimmerTime = 500L
    private lateinit var handler: Handler

    companion object {
         var genre : GenreSelected? = null
    }

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter ({ it: Result?, imageView: ImageView? ->
            val movieClicked = it
            movieClicked?.let{ result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(BASE_FILM_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 1)

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
                intent.putExtra(BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(BASE_FILM_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 1)

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
                intent.putExtra(BASE_FILM_DETAILED_KEY, movieComplete)
                intent.putExtra(BASE_FILM_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 1)

                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imageView,"sharedImgView")
                startActivity(intent,options.toBundle())
            }
        }, {movie ->
            setLongClickDialog(movie)
        })
    }

    private val homeAdapter4 : DiscoverTvAdapter by lazy {
        DiscoverTvAdapter ({ result, imageView ->
            result?.let {
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_SERIE_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 2)

                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,imageView,"sharedImgView")
                startActivity(intent,options.toBundle())
            }
        }, {serie ->
            setLongClickDialogSerie(serie)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selected = arguments?.getParcelable<GenreSelected>("Selected")
        genre = selected
        tutorial = arguments?.getInt(TUTORIAL)

        handler = Handler(Looper.getMainLooper())

       activity?.let{
            viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
            setupRecyclerView()

           if(tutorial == 0) {
               MaterialAlertDialogBuilder(it)
                       .setTitle("Tutorial")
                       .setMessage("Gostaria de ver o tutorial?")
                       .setNegativeButton("Não") { dialog, which ->
                           dialog.dismiss()
                           MenuActivity.USER.tutorial = 1
                           viewModel.updateUser(MenuActivity.USER)
                       }
                       .setPositiveButton("Sim") { dialog, which ->
                           tutorialImplementation()
                       }
                       .show()
           }
        }

        val userDao = context?.let { MadeInBrazilDatabase.getDatabase(it) }?.userDao()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun tutorialImplementation() {
        TapTargetSequence(activity).targets(
                TapTarget.forView(binding.logoMadeInBrasil,
                        getString(R.string.string_welcome_tutorial_title),
                        getString(R.string.string_welcome_tutorial_description))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorAccentOpaque)
                        .targetCircleColor(R.color.colorAccent)
                        .transparentTarget(true).targetRadius(100),
                TapTarget.forView(binding.rvCardsListLancamentos,
                        getString(R.string.string_cards_tutorial_title),
                getString(R.string.string_cards_tutorial_description))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorAccentOpaque)
                        .targetCircleColor(R.color.colorAccent)
                        .transparentTarget(true).targetRadius(220)
        ).listener(object: TapTargetSequence.Listener{
            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
            override fun onSequenceFinish() {
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(TUTORIAL, 89214)
                intent.putExtra(ID_FRAGMENTS, 2)
                startActivity(intent)
            }
            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
        }).start()
    }

    private fun loadContentUpcoming() {
        viewModel.upcomingMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->

                homeAdapter2.currentList?.clear()
                homeAdapter2.submitList(pagedList, null)
                homeAdapter2.notifyDataSetChanged()
                if(pagedList.isEmpty())
                    binding?.containeFuturosrLancamentos?.visibility = View.GONE
            else
                handler.postDelayed({ binding?.shimmerFuturosLancamentos?.visibility = View.GONE },takeShimmerTime)
        }
    }

    private fun loadContentNowPlaying() {
        viewModel.nowPlayingMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->

                homeAdapter.currentList?.clear()
                homeAdapter.submitList(pagedList, null)
                homeAdapter.notifyDataSetChanged()
                if(pagedList.isNotEmpty())
                    binding?.containerLancamentos?.visibility = View.GONE
                else
                    handler.postDelayed({ binding?.shimmerLancamentos?.visibility = View.GONE },takeShimmerTime)
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
            if(pagedList.isNotEmpty())
                binding?.containerListsSeries?.visibility = View.GONE
            else
                handler.postDelayed({binding?.shimmerSeriesVoce?.visibility = View.GONE},takeShimmerTime)
        }
    }

    private fun setupRecyclerView() {
        binding.rvCardsListLancamentos.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentNowPlaying()
            adapter = homeAdapter
        }

        binding.rvCardsListFuturosLancamentos.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentUpcoming()
            adapter = homeAdapter2
        }

        binding.rvCardsListMovies.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            loadContentDiscoverMovie()
            adapter = homeAdapter3
        }

        binding.rvCardListSeries.apply {
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

            serie?.let {
                if(MenuActivity.USER.favorites.contains(it.id)) {
                    dialog.cbFavorite.isChecked = true
                }
                if(MenuActivity.USER.watched.contains(it.id)) {
                    dialog.cbWatched.isChecked = true
                }
            }

            dialog.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
                serie?.let {
                    if(isChecked) {
                        MenuActivity.USER.favorites.add(it.id)
                    }else {
                        MenuActivity.USER.favorites.remove(it.id)
                    }

                    viewModel.updateUser(MenuActivity.USER)
                }
            }

            dialog.cbWatched.setOnCheckedChangeListener { _, isChecked ->
                serie?.let {
                    if(isChecked) {
                        MenuActivity.USER.watched.add(it.id)
                    }else {
                        MenuActivity.USER.watched.remove(it.id)
                    }

                    viewModel.updateUser(MenuActivity.USER)
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

            movie?.let {
                if(MenuActivity.USER.favorites.contains(it.id)) {
                    dialog.cbFavorite.isChecked = true
                }
                if(MenuActivity.USER.watched.contains(it.id)) {
                    dialog.cbWatched.isChecked = true
                }
            }

            dialog.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
                movie?.let {
                    if(isChecked) {
                        MenuActivity.USER.favorites.add(it.id)
                    }else {
                        MenuActivity.USER.favorites.remove(it.id)
                    }

                    viewModel.updateUser(MenuActivity.USER)
                }
            }

            dialog.cbWatched.setOnCheckedChangeListener { _, isChecked ->
                movie?.let {
                    if(isChecked) {
                        MenuActivity.USER.watched.add(it.id)
                    }else {
                        MenuActivity.USER.watched.remove(it.id)
                    }

                    viewModel.updateUser(MenuActivity.USER)
                }
            }

            dialog.show()
        }
    }
}