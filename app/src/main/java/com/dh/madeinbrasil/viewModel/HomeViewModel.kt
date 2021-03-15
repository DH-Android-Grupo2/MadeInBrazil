package com.dh.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.model.discover.DiscoverMovieDataSourceFactory
import com.dh.madeinbrasil.model.discoverTV.DiscoverTvDataSourceFactory
import com.dh.madeinbrasil.model.nowPlaying.NowPlayingDataSourceFactory
import com.dh.madeinbrasil.model.search.ResultSearch
import com.dh.madeinbrasil.model.upcoming.Result
import com.dh.madeinbrasil.model.upcoming.UpcomingDataSourceFactory
import com.dh.madeinbrasil.repository.FragmentsRepository
import com.dh.madeinbrasil.utils.Constants.Paging.PAGE_SIZE
import kotlinx.coroutines.launch

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
    private val repository by lazy {
        FragmentsRepository()
    }

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

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }
}
