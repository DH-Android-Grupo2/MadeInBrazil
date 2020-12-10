package com.example.madeinbrasil.model.discoverTV

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.model.search.ResultSearch


class DiscoverTvDataSourceFactory : DataSource.Factory<Int, ResultSearch>(){

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, ResultSearch>>()


    override fun create(): DataSource<Int, ResultSearch> {

        val tmdbDataSource = DiscoverTvPageKeyedDataSource()


        tmdbLiveDataSource.postValue(tmdbDataSource)

        return  tmdbDataSource
    }


    fun getSearchLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int,ResultSearch>> {
        return tmdbLiveDataSource
    }
}