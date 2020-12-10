package com.example.madeinbrasil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.search.serie.SearchSerieDataSourceFactory
import com.example.madeinbrasil.utils.Constants

class SelectSerieViewModel: ViewModel() {
    var searchSeriePagedList: LiveData<PagedList<ResultSearch>>? = null
    private var searchSerieLiveDataSource: LiveData<PageKeyedDataSource<Int, ResultSearch>>? = null
    var query = " "

    var clikedItemId: MutableLiveData<Int> = MutableLiveData()

    init {
        searchSerieData()
    }

    fun setQuerySerie(newQuerySerie: String) {
        this.query = newQuerySerie
        searchSerieData()
    }

    private fun searchSerieData() {

        val tmdbSourceFactory = SearchSerieDataSourceFactory(query)
        searchSerieLiveDataSource = tmdbSourceFactory.getSearchSerieLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.Paging.PAGE_SIZE).build()

        searchSeriePagedList = LivePagedListBuilder(tmdbSourceFactory, pagedListConfig).build()
    }

    fun postClikedItemId(id: Int) {
        clikedItemId.postValue(id)
    }
}