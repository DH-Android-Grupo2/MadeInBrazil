package com.example.madeinbrasil.view.fragment

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.SerieAdapter
import com.example.madeinbrasil.databinding.FragmentSeriesBinding
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.view.activity.MenuActivity
import com.example.madeinbrasil.viewModel.SerieViewModel
import com.example.madeinbrasil.view.activity.UserActivity
import kotlinx.android.synthetic.main.filmsseries_popup.*

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
        var application = getActivity()?.applicationContext
        setUpSearchView(application as Application)
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

    private fun setUpSearchView(application: Application) {
        val searchView = binding?.tilSearchSeries

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setQuerySerie(application,query)
                binding?.tvMessageSeries?.isVisible = query != ""
                binding?.animationSeries?.isVisible = query != ""
                setUpRecyclerView()
                loadContentSerie()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setQuerySerie(application,newText)
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

            value?.let {
                if(MenuActivity.USER.favorites.contains(it.id)) {
                    dialog.cbFavorite.isChecked = true
                }
                if(MenuActivity.USER.watched.contains(it.id)) {
                    dialog.cbWatched.isChecked = true
                }
            }

            dialog.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
                value?.let {
                    if(isChecked) {
                        MenuActivity.USER.favorites.add(it.id)
                    }else {
                        MenuActivity.USER.favorites.remove(it.id)
                    }

                    viewModel.updateUser(MenuActivity.USER)
                }
            }

            dialog.cbWatched.setOnCheckedChangeListener { _, isChecked ->
                value?.let {
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