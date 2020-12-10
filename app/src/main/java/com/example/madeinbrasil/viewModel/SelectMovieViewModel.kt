package com.example.madeinbrasil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.model.search.movie.searchMovieDataSourceFactory
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants

class SelectMovieViewModel: ViewModel() {
    var searchMoviePagedList: LiveData<PagedList<Result>>? = null
    private var searchMovieLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null
    private var query = ""

    var clickedMovieItem: MutableLiveData<Result> = MutableLiveData()

    init {
        searchData()
    }

    fun setQuery(newQuery:String){
        this.query = newQuery
        searchData()
    }

    fun searchData(){

        val tmdbDataSourceFactory = searchMovieDataSourceFactory(query)

        searchMovieLiveDataSource = tmdbDataSourceFactory.getSearchLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.Paging.PAGE_SIZE)
                .build()

        searchMoviePagedList = LivePagedListBuilder(tmdbDataSourceFactory, pagedListConfig)
                .build()
    }

    fun postClickedItem(movie: Result) {
        clickedMovieItem.postValue(movie)
    }

}