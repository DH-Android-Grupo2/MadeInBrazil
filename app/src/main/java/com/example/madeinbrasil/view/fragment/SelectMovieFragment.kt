package com.example.madeinbrasil.view.fragment

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
import com.example.madeinbrasil.databinding.FragmentSelectMovieBinding
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_MOVIES
import com.example.madeinbrasil.viewModel.SelectMovieViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectMovieFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSelectMovieBinding
    private lateinit var viewModel: SelectMovieViewModel

    private val selectMovieAdapter by lazy {
        arguments?.getIntArray(SELECTED_MOVIES)?.let {SelectMovieAdapter(it.toMutableList())}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetupSearchView()
        setupRecyclerView()
        activity?.let{
            viewModel = ViewModelProvider(it).get(SelectMovieViewModel::class.java)
            loadContentSearch()
        }

        selectMovieAdapter?.onItemClick = {
            viewModel.postClickedItem(it)
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

    }

    private fun SetupSearchView() {

        val searchView: SearchView = binding.searchField

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String):Boolean {
                viewModel.setQuery(query)
                loadContentSearch()
                binding.tvMessageFilmsAdd.isVisible = query == ""
                binding.animationFilmsAdd.isVisible = query == ""
                return true
            }

            override fun onQueryTextChange(newText: String):Boolean{
                viewModel.setQuery(newText)
                loadContentSearch()
                binding.tvMessageFilmsAdd.isVisible = newText == ""
                binding.animationFilmsAdd.isVisible = newText == ""
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvShows.apply {
            layoutManager = GridLayoutManager(this@SelectMovieFragment.context, 2)
            adapter = selectMovieAdapter
        }
    }

    private fun loadContentSearch() {
        viewModel.searchMoviePagedList?.observe(viewLifecycleOwner) { pagedList ->
            selectMovieAdapter?.submitList(pagedList)
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