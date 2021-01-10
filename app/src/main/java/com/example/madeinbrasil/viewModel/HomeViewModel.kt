package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.model.discover.DiscoverMovieDataSourceFactory
import com.example.madeinbrasil.model.discoverTV.DiscoverTvDataSourceFactory
import com.example.madeinbrasil.model.nowPlaying.NowPlayingDataSourceFactory
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.model.upcoming.UpcomingDataSourceFactory
import com.example.madeinbrasil.utils.Constants.Paging.PAGE_SIZE

class HomeViewModel(
        application: Application
): AndroidViewModel(application) {

    var upcomingMoviePagedList: LiveData<PagedList<Result>>? = null
    var nowPlayingMoviePagedList: LiveData<PagedList<Result>>? = null
    var discoverMoviePagedList: LiveData<PagedList<Result>>? = null
    var discoverTvPagedList: LiveData<PagedList<ResultSearch>>? = null
    private var upcomingLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null
    private var nowPlayingLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null
    private var discoverMovieLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null
    private var discoverTvLiveDataSource: LiveData<PageKeyedDataSource<Int,ResultSearch>>? = null

    init {
        nowPlayingData(application)
        upcomingData(application)
        discoverMovieData(application)
        discoverTvData(application)

    }

    fun nowPlayingData(application: Application){
        val tmdbDataSourceFactory = NowPlayingDataSourceFactory(application)

        nowPlayingLiveDataSource = tmdbDataSourceFactory.getSearchLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        nowPlayingMoviePagedList = LivePagedListBuilder(tmdbDataSourceFactory, pagedListConfig)
            .build()
    }

    fun upcomingData(application: Application){
        val tmdbDataSourceFactory = UpcomingDataSourceFactory(application)

        upcomingLiveDataSource = tmdbDataSourceFactory.getSearchLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        upcomingMoviePagedList = LivePagedListBuilder(tmdbDataSourceFactory, pagedListConfig)
            .build()
    }



    fun discoverMovieData(application: Application){
        val tmdbDataSourceFactory = DiscoverMovieDataSourceFactory(application)

        discoverMovieLiveDataSource = tmdbDataSourceFactory.getSearchLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE).build()

        discoverMoviePagedList = LivePagedListBuilder(tmdbDataSourceFactory, pagedListConfig)
                .build()
    }


    fun discoverTvData(application: Application){

        val tmdbDataSourceFactory = DiscoverTvDataSourceFactory(application)

        discoverTvLiveDataSource = tmdbDataSourceFactory.getSearchLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE).build()


        discoverTvPagedList = LivePagedListBuilder(tmdbDataSourceFactory, pagedListConfig)
                .build()
    }



}
