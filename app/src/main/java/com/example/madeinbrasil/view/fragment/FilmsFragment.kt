package com.example.madeinbrasil.view.fragment

import android.content.Context
import android.content.Intent
import android.icu.text.IDNA
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.FilmsAdapter
import com.example.madeinbrasil.databinding.FragmentFilmsBinding
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.example.madeinbrasil.viewModel.FilmsViewModel
import com.example.madeinbrasil.view.activity.UserActivity
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import kotlinx.android.synthetic.main.fragment_films.*
import java.util.*


class FilmsFragment : Fragment() {
    private var binding: FragmentFilmsBinding? = null
    private lateinit var viewModel: FilmsViewModel

    private val filmsAdapter : FilmsAdapter by lazy {
        FilmsAdapter {
            val movieClicked = it
            movieClicked?.let{result->
                val intent = Intent(activity, FilmsAndSeriesActivity::class.java)
                intent.putExtra(BASE_FILM_KEY, result)
                intent.putExtra(ID_FRAGMENTS, 1)
                startActivity(intent)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivProfileFilms?.setOnClickListener {
            this.context?.let { it1 -> startUserActivity(it1) }
        }
        SetupSearchView()
        activity?.let{
            viewModel = ViewModelProvider(this).get(FilmsViewModel::class.java)
            setupRecyclerView()
            loadContentSearchMovie()
        }
//        tutorialImplementation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

//    private fun tutorialImplementation() {
//        TapTargetSequence(activity).targets(
//                TapTarget.forView(binding?.tilSearchFilms,
//                        getString(R.string.string_search_tutorial_title),
//                        getString(R.string.string_search_tutorial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(40),
//                TapTarget.forView(binding?.ivProfileFilms,
//                        getString(R.string.string_profile_tutorial_title),
//                       getString(R.string.string_profile_tutotial_description))
//                        .cancelable(false)
//                        .outerCircleColor(R.color.colorAccentOpaque)
//                        .targetCircleColor(R.color.colorAccent)
//                        .transparentTarget(true).targetRadius(40)
//        ).listener(object: TapTargetSequence.Listener{
//            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
//            override fun onSequenceFinish() {}
//            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
//        }).start()
//    }

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

    private fun SetupSearchView() {

        val searchView:SearchView? = binding?.tilSearchFilms
        searchView?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String):Boolean {
                viewModel.setQuery(query)
                Log.i("Query","${viewModel.getQuery()}")
                binding?.tvMessageFilms?.isVisible = query != ""
                binding?.animationFilms?.isVisible = query != ""
                setupRecyclerView()
                loadContentSearchMovie()
                return true
            }
            override fun onQueryTextChange(newText: String):Boolean{
                viewModel.setQuery(newText)
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

}

