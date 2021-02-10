package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.model.search.movie.searchMovieDataSourceFactory
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.repository.FragmentsRepository
import com.example.madeinbrasil.utils.Constants
import kotlinx.coroutines.launch


class FilmsViewModel(
        application: Application
) : AndroidViewModel(application) {


    var searchMoviePagedList: LiveData<PagedList<Result>>? = null
    private var searchMovieLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null
    private var query = ""
    private val repository by lazy {
        FragmentsRepository()
    }

    init {
        searchData(application)
    }


    fun setQuery(application: Application,newQuery:String){
        this.query = newQuery
        searchData(application)
    }

    fun getQuery():String{
        return query
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

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }
}