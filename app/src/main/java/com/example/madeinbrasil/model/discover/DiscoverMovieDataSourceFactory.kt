package com.example.madeinbrasil.model.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.model.upcoming.Result


class DiscoverMovieDataSourceFactory() : DataSource.Factory<Int, Result>(){

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Result>>()

    override fun create(): DataSource<Int, Result> {
        //getting our data source object
        val tmdbDataSource = DiscoverMoviePageKeyedDataSource()

        //posting the datasource to get the values
        tmdbLiveDataSource.postValue(tmdbDataSource)

        //returning the datasource
        return tmdbDataSource
    }

    fun getSearchLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Result>> {
        return tmdbLiveDataSource
    }

}


