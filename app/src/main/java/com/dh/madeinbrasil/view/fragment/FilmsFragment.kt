package com.dh.madeinbrasil.view.fragment

import android.app.Application
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
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.adapter.FilmsAdapter
import com.dh.madeinbrasil.databinding.FragmentFilmsBinding
import com.dh.madeinbrasil.model.upcoming.Result
import com.dh.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.dh.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.dh.madeinbrasil.utils.Constants.ConstantsFilms.TUTORIAL
import com.dh.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.dh.madeinbrasil.view.activity.MenuActivity
import com.dh.madeinbrasil.viewModel.FilmsViewModel
import com.dh.madeinbrasil.view.activity.UserActivity
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import kotlinx.android.synthetic.main.filmsseries_popup.*


class FilmsFragment() : Fragment() {
    private var binding: FragmentFilmsBinding? = null
    private lateinit var viewModel: FilmsViewModel
    private var tutorial: Int? = null

    private val filmsAdapter : FilmsAdapter by lazy {
        FilmsAdapter ({
            val movieClicked = it
            movieClicked?.let{result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_FILM_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 1)
                startActivity(intent)
            }
        }, {value -> setLongClickDialog(value) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        val bundle = arguments

        tutorial = bundle?.getInt(TUTORIAL)
        binding?.ivProfileFilms?.setOnClickListener {
            this.context?.let { it1 -> startUserActivity(it1) }
        }
       var application = getActivity()?.applicationContext
        SetupSearchView(application as Application)
        activity?.let{
            viewModel = ViewModelProvider(this).get(FilmsViewModel::class.java)
            setupRecyclerView()
            loadContentSearchMovie()
        }

        if(tutorial == 0) {
            tutorialImplementation()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    private fun tutorialImplementation() {
        TapTargetSequence(activity).targets(
                TapTarget.forView(binding?.tilSearchFilms,
                        getString(R.string.string_search_tutorial_title),
                        getString(R.string.string_search_tutorial_description))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorAccentOpaque)
                        .targetCircleColor(R.color.colorAccent)
                        .transparentTarget(true).targetRadius(40),
                TapTarget.forView(binding?.ivProfileFilms,
                        getString(R.string.string_profile_tutorial_title),
                       getString(R.string.string_profile_tutotial_description))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorAccentOpaque)
                        .targetCircleColor(R.color.colorAccent)
                        .transparentTarget(true).targetRadius(40)
        ).listener(object: TapTargetSequence.Listener{
            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
            override fun onSequenceFinish() {
                val intent = Intent(activity, UserActivity::class.java)
                intent.putExtra(TUTORIAL, 0)
                startActivity(intent)
            }
            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
        }).start()
    }

    private fun loadContentSearchMovie() {
        viewModel.searchMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            filmsAdapter.submitList(pagedList)
        }
    }

    private fun setupRecyclerView() {
        binding?.rvCardsListFilms?.apply {
            layoutManager = GridLayoutManager(this@FilmsFragment.context,2)
            adapter = filmsAdapter
        }

    }

    private fun SetupSearchView(application: Application) {

        val searchView:SearchView? = binding?.tilSearchFilms
        searchView?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String):Boolean {
                viewModel.setQuery(application,query)
                Log.i("Query","${viewModel.getQuery()}")
                binding?.tvMessageFilms?.isVisible = query != ""
                binding?.animationFilms?.isVisible = query != ""
                setupRecyclerView()
                loadContentSearchMovie()
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.setQuery(application,newText)
                }
                Log.i("Query","${viewModel.getQuery()}")
                binding?.tvMessageFilms?.isVisible = newText == ""
                binding?.animationFilms?.isVisible = newText == ""
                setupRecyclerView()
                loadContentSearchMovie()
                return true
            }
        })
    }

    private fun startUserActivity(context: Context) {
        val intent = Intent(context, UserActivity::class.java)
        startActivity(intent)
    }

    private fun setLongClickDialog(value: Result?) {
        activity?.let {activity ->
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.filmsseries_popup)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            Glide.with(activity)
                .load(value?.posterPath)
                .placeholder(R.drawable.logo_made_in_brasil)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(dialog.ivDialogPoster)

            dialog.tvDialogName.text = value?.title
            dialog.cbShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND

                    putExtra(Intent.EXTRA_TEXT, "Filme: ${value?.title} by MadeInBrasil")
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TITLE, "Filme: ${value?.title} \nShared by MadeInBrasil")
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

