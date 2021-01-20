package com.example.madeinbrasil.view.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.SerieAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.databinding.FragmentSeriesBinding
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.viewModel.SerieViewModel
import com.example.madeinbrasil.view.activity.UserActivity
import com.example.madeinbrasil.view.adapter.MainAdapterSeries
import kotlinx.android.synthetic.main.filmsseries_popup.*
import kotlinx.coroutines.launch

class SeriesFragment : Fragment() {
    private val seriesAdapter: SerieAdapter by lazy {
        SerieAdapter ({result ->
            result?.let {
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_SERIE_KEY, it)
                intent.putExtra(ID_FRAGMENTS, 2)
                startActivity(intent)
            }
        }, {value -> setLongClickDialog(value) })
    }

    private var binding: FragmentSeriesBinding? = null
    private lateinit var viewModel: SerieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivProfileSeries?.setOnClickListener {
            this.context?.let { it1 -> startUserActivity(it1) }
        }
        setUpSearchView()
        activity?.let {
            viewModel = ViewModelProvider(this).get(SerieViewModel::class.java)
            setUpRecyclerView()
            loadContentSerie()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    private fun setUpRecyclerView() {
        binding?.rvCardsListSeries?.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = seriesAdapter
        }
    }

    private fun loadContentSerie() {
        viewModel.searchSeriePagedList?.observe(viewLifecycleOwner) { pagedList ->
            seriesAdapter.submitList(pagedList)
        }
    }

    private fun setUpSearchView() {
        val searchView = binding?.tilSearchSeries

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setQuerySerie(query)
                binding?.tvMessageSeries?.isVisible = query != ""
                binding?.animationSeries?.isVisible = query != ""
                setUpRecyclerView()
                loadContentSerie()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setQuerySerie(newText)
                binding?.tvMessageSeries?.isVisible = newText == ""
                binding?.animationSeries?.isVisible = newText == ""
                setUpRecyclerView()
                loadContentSerie()
                return true
            }

        })
    }

    private fun startUserActivity(context: Context) {
        val intent = Intent(context, UserActivity::class.java)
        startActivity(intent)
    }

    private fun setLongClickDialog(value: ResultSearch?) {
        activity?.let {activity ->
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.filmsseries_popup)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            Glide.with(activity)
                .load(value?.posterPath)
                .placeholder(R.drawable.logo_made_in_brasil)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(dialog.ivDialogPoster)

            dialog.tvDialogName.text = value?.name
            dialog.cbShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND

                    putExtra(Intent.EXTRA_TEXT, "Série: ${value?.name} by MadeInBrasil")
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TITLE, "Série: ${value?.name} \nShared by MadeInBrasil")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                }
                val shareIntent = Intent.createChooser(sendIntent, "null")
                ContextCompat.startActivity(it.context, shareIntent, null)
            }

            dialog.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    val dbFav = MadeInBrazilDatabase.getDatabase(activity).favoriteDao()
                    val dbMidia = MadeInBrazilDatabase.getDatabase(activity).midiaDao()

                    value?.let {
                        val fav = Favorites(it.id, it.id, isChecked)
                        val midia = MidiaEntity(value.id, value.backdropPath, "", "",
                                "", value.overview, 0.0, value.posterPath, "",
                                0, "", value.voteAverage, 0, listOf(), value.firstAirDate,
                                value.name, midiaType = 2)

                        if(isChecked) {
                            dbFav.insertFavorite(fav)
                            dbMidia.insertMidia(midia)
                        }else {
                            dbFav.deleteByIdFavorites(it.id)
                        }
                    }
                }
            }

            dialog.cbWatched.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    val dbWatched = MadeInBrazilDatabase.getDatabase(activity).watchedDao()
                    val dbMidia = MadeInBrazilDatabase.getDatabase(activity).midiaDao()

                    value?.let {
                        val watched = Watched(it.id, it.id, isChecked)
                        val midia = MidiaEntity(value.id, value.backdropPath, "", "",
                            "", value.overview, 0.0, value.posterPath, "",
                            0, "", value.voteAverage, 0, listOf(), value.firstAirDate,
                                value.name, midiaType = 2)

                        if(isChecked) {
                            dbWatched.insertWatched(watched)
                            dbMidia.insertMidia(midia)
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
                    if(it.midia.id == value?.id) {
                        it.favorites.forEach {
                            dialog.cbFavorite.isChecked = it.isChecked
                        }
                    }
                }

                dbWatched.getMidiaWithWatched().forEach {
                    if(it.midia.id == value?.id) {
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