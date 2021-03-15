package com.dh.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.model.search.ResultSearch
import com.dh.madeinbrasil.model.search.serie.SearchSerieDataSourceFactory
import com.dh.madeinbrasil.repository.FragmentsRepository
import com.dh.madeinbrasil.utils.Constants.Paging.PAGE_SIZE
import kotlinx.coroutines.launch

class SerieViewModel(application: Application): AndroidViewModel(application) {
    var searchSeriePagedList: LiveData<PagedList<ResultSearch>>? = null
    private var searchSerieLiveDataSource: LiveData<PageKeyedDataSource<Int, ResultSearch>>? = null
    var query = " "
    private val repository by lazy {
        FragmentsRepository()
    }

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

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }
}