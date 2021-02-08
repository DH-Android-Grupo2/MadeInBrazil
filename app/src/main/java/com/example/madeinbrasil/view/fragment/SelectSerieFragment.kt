package com.example.madeinbrasil.view.fragment

import android.app.Application
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.adapter.SelectMovieAdapter
import com.example.madeinbrasil.adapter.SelectSerieAdapter
import com.example.madeinbrasil.databinding.FragmentSelectSerieBinding
import com.example.madeinbrasil.model.customLists.ListMediaItem
import com.example.madeinbrasil.model.customLists.firebase.Media
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_SERIES
import com.example.madeinbrasil.viewModel.SelectSerieViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectSerieFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSelectSerieBinding
    private lateinit var viewModel: SelectSerieViewModel

    private val selectSerieAdapter by lazy {
        arguments?.getStringArray(SELECTED_SERIES)?.let{ SelectSerieAdapter(it.toMutableList()) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectSerieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var application = activity?.applicationContext
        SetupSearchView(application as Application)
        setupRecyclerView()
        activity?.let{
            viewModel = ViewModelProvider(it).get(SelectSerieViewModel::class.java)
            loadContentSearch()
        }

        selectSerieAdapter?.onItemClick = {
//            val media = ListMediaItem(it.id.toLong(), it.name, it.backdropPath, it.originalTitle)
            val media = Media(it.id.toString(), it.backdropPath, it.originalLanguage, it.originalTitle, it.overview,
                it.popularity, it.posterPath, it.releaseDate, it.title, it.voteAverage?.toDouble(),
                it.voteCount, it.firstAirDate, it.name)
            viewModel.postClickedItem(media)
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

    }

    private fun SetupSearchView(
            application: Application
    ) {

        val searchView: SearchView = binding.searchField

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String):Boolean {
                viewModel.setQuerySerie(application,query)
                loadContentSearch()
                binding.tvMessageSeriesAdd.isVisible = query == ""
                binding.animationSeriesAdd.isVisible = query == ""
                return true
            }

            override fun onQueryTextChange(newText: String):Boolean{
                viewModel.setQuerySerie(application,newText)
                loadContentSearch()
                binding.tvMessageSeriesAdd.isVisible = newText == ""
                binding.animationSeriesAdd.isVisible = newText == ""
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvShows.apply {
            layoutManager = GridLayoutManager(this@SelectSerieFragment.context, 2)
            adapter = selectSerieAdapter
        }

    }

    private fun loadContentSearch() {
        viewModel.searchSeriePagedList?.observe(viewLifecycleOwner) { pagedList ->
            selectSerieAdapter?.submitList(pagedList)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val d = super.onCreateDialog(savedInstanceState)

        d.setOnShowListener {
            setUpRatio(it as BottomSheetDialog)
        }

        return d
    }

    private fun setUpRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet as FrameLayout)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = Resources.getSystem().displayMetrics.heightPixels
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}