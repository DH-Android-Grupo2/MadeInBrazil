package com.example.madeinbrasil.view.fragment

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.adapter.SelectSerieAdapter
import com.example.madeinbrasil.databinding.FragmentSelectSerieBinding
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_SERIES
import com.example.madeinbrasil.viewmodel.SelectSerieViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectSerieFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSelectSerieBinding
    private lateinit var viewModel: SelectSerieViewModel

    private val selectSerieAdapter by lazy {
        arguments?.getIntArray(SELECTED_SERIES)?.let{ SelectSerieAdapter(it.toMutableList()) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectSerieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetupSearchView()
        setupRecyclerView()
        activity?.let{
            viewModel = ViewModelProvider(it).get(SelectSerieViewModel::class.java)
            loadContentSearch()
        }

        selectSerieAdapter?.onItemClick = {
            viewModel.postClikedItemId(it)
        }
    }

    private fun SetupSearchView() {

        val searchView: SearchView = binding.searchField

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String):Boolean {
                viewModel.setQuerySerie(query)
                loadContentSearch()
                return true
            }

            override fun onQueryTextChange(newText: String):Boolean{
                viewModel.setQuerySerie(newText)
                loadContentSearch()
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