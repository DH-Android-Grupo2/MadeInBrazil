package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.model.customLists.ListMediaItem
import com.example.madeinbrasil.model.search.movie.searchMovieDataSourceFactory
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants

class SelectMovieViewModel(
        application: Application
): AndroidViewModel(application) {
    var searchMoviePagedList: LiveData<PagedList<Result>>? = null
    private var searchMovieLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null
    private var query = ""

    var clickedMovieItem: MutableLiveData<ListMediaItem> = MutableLiveData()

    init {
        searchData(application)
    }

    fun setQuery(
            application: Application,
            newQuery:String){
        this.query = newQuery
        searchData(application)
    }

    fun searchData(application: Application){

        val tmdbDataSourceFactory = searchMovieDataSourceFactory(application,query)

        searchMovieLiveDataSource = tmdbDataSourceFactory.getSearchLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.Paging.PAGE_SIZE)
                .build()

        searchMoviePagedList = LivePagedListBuilder(tmdbDataSourceFactory, pagedListConfig)
                .build()
    }

    fun postClickedItem(movie: ListMediaItem) {
        clickedMovieItem.postValue(movie)
    }

}