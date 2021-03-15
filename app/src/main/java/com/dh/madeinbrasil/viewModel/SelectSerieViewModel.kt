package com.dh.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.dh.madeinbrasil.model.customLists.firebase.Media
import com.dh.madeinbrasil.model.search.ResultSearch
import com.dh.madeinbrasil.model.search.serie.SearchSerieDataSourceFactory
import com.dh.madeinbrasil.utils.Constants

class SelectSerieViewModel(
        application: Application
): AndroidViewModel(application) {
    var searchSeriePagedList: LiveData<PagedList<ResultSearch>>? = null
    private var searchSerieLiveDataSource: LiveData<PageKeyedDataSource<Int, ResultSearch>>? = null
    var query = " "

    var clickedSerieItem: MutableLiveData<Media> = MutableLiveData<Media>()


    init {
        searchSerieData(application)
    }

    fun setQuerySerie(application: Application,newQuerySerie: String) {
        this.query = newQuerySerie
        searchSerieData(application)
    }

    private fun searchSerieData(application: Application) {

        val tmdbSourceFactory = SearchSerieDataSourceFactory(application,query)
        searchSerieLiveDataSource = tmdbSourceFactory.getSearchSerieLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.Paging.PAGE_SIZE).build()

        searchSeriePagedList = LivePagedListBuilder(tmdbSourceFactory, pagedListConfig).build()
    }

    fun postClickedItem(serie: Media) {
        clickedSerieItem.postValue(serie)
    }
}