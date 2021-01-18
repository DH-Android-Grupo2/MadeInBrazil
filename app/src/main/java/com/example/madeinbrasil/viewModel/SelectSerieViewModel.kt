package com.example.madeinbrasil.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.model.customLists.ListMediaItem
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.search.serie.SearchSerieDataSourceFactory
import com.example.madeinbrasil.utils.Constants

class SelectSerieViewModel: ViewModel() {
    var searchSeriePagedList: LiveData<PagedList<ResultSearch>>? = null
    private var searchSerieLiveDataSource: LiveData<PageKeyedDataSource<Int, ResultSearch>>? = null
    var query = " "

    var clickedSerieItem: MutableLiveData<ListMediaItem> = MutableLiveData<ListMediaItem>()


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

    fun postClickedItem(serie: ListMediaItem) {
        clickedSerieItem.postValue(serie)
    }
}