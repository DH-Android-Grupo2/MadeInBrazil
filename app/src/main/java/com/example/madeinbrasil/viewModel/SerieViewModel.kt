package com.example.madeinbrasil.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.search.serie.SearchSerieDataSourceFactory
import com.example.madeinbrasil.utils.Constants.Paging.PAGE_SIZE

class SerieViewModel(application: Application): AndroidViewModel(application) {
    var searchSeriePagedList: LiveData<PagedList<ResultSearch>>? = null
    private var searchSerieLiveDataSource: LiveData<PageKeyedDataSource<Int, ResultSearch>>? = null
    var query = " "

    init {
        searchSerieData(application)
    }

    fun setQuerySerie(
            application: Application,
            newQuerySerie: String) {
        this.query = newQuerySerie
        searchSerieData(application)
    }

    fun getQuerySerie(): String {
        return query
    }

    private fun searchSerieData(application: Application) {

        val tmdbSourceFactory = SearchSerieDataSourceFactory(application,query)
        searchSerieLiveDataSource = tmdbSourceFactory.getSearchSerieLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        searchSeriePagedList = LivePagedListBuilder(tmdbSourceFactory, pagedListConfig).build()
    }
}