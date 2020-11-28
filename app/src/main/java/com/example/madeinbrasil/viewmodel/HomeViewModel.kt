package com.example.madeinbrasil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.paging.TmdbDataSourceFactory
import com.example.madeinbrasil.utils.Constants.Paging.PAGE_SIZE

class HomeViewModel: ViewModel() {

    var moviePagedList: LiveData<PagedList<Result>>? = null
    private var tmdbLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null

    init {
        val tmdbDataSourceFactory = TmdbDataSourceFactory()

        tmdbLiveDataSource = tmdbDataSourceFactory.getSearchLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        moviePagedList = LivePagedListBuilder(tmdbDataSourceFactory, pagedListConfig)
            .build()
    }

}