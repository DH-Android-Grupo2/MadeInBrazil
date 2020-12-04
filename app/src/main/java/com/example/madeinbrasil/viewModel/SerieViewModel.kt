package com.example.madeinbrasil.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.search.serie.SearchSerieDataSourceFactory
import com.example.madeinbrasil.utils.Constants.Paging.PAGE_SIZE

class SerieViewModel: ViewModel() {
    var searchSeriePagedList: LiveData<PagedList<ResultSearch>>? = null
    private var searchSerieLiveDataSource: LiveData<PageKeyedDataSource<Int, ResultSearch>>? = null
    var query = " "

    init {
        searchSerieData()
    }

    fun setQuerySerie(newQuerySerie: String) {
        this.query = newQuerySerie
        searchSerieData()
    }

    fun getQuerySerie(): String {
        return query
    }

    private fun searchSerieData() {

        val tmdbSourceFactory = SearchSerieDataSourceFactory(query)
        searchSerieLiveDataSource = tmdbSourceFactory.getSearchSerieLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        searchSeriePagedList = LivePagedListBuilder(tmdbSourceFactory, pagedListConfig).build()
    }
}