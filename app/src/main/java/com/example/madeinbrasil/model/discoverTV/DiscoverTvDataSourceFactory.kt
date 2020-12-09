package com.example.madeinbrasil.model.discoverTV

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource


class DiscoverTvDataSourceFactory : DataSource.Factory<Int, Result>(){

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Result>>()


    override fun create(): DataSource<Int, Result> {

        val tmdbDataSource = DiscoverTvPageKeyedDataSource()


        tmdbLiveDataSource.postValue(tmdbDataSource)

        return  tmdbDataSource
    }


    fun getSearchLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int,Result>> {
        return tmdbLiveDataSource
    }
}