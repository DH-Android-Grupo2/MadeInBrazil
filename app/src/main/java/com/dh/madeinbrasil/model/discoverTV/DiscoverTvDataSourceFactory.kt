package com.dh.madeinbrasil.model.discoverTV

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.dh.madeinbrasil.model.search.ResultSearch


class DiscoverTvDataSourceFactory(
    private val context: Context
) : DataSource.Factory<Int, ResultSearch>(){

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, ResultSearch>>()


    override fun create(): DataSource<Int, ResultSearch> {

        val tmdbDataSource = DiscoverTvPageKeyedDataSource(context)


        tmdbLiveDataSource.postValue(tmdbDataSource)

        return  tmdbDataSource
    }


    fun getSearchLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int,ResultSearch>> {
        return tmdbLiveDataSource
    }
}