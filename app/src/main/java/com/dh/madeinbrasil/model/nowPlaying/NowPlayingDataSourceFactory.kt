package com.dh.madeinbrasil.model.nowPlaying

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.dh.madeinbrasil.model.upcoming.Result

class NowPlayingDataSourceFactory(
    private val context: Context
) : DataSource.Factory<Int, Result>() {

    //creating the mutable live data
    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Result>>()

    override fun create(): DataSource<Int, Result> {
        //getting our data source object
        val tmdbDataSource = NowPlayingPageKeyedDataSource(context)

        //posting the datasource to get the values
        tmdbLiveDataSource.postValue(tmdbDataSource)

        //returning the datasource
        return tmdbDataSource
    }

    //getter for itemlivedatasource
    fun getSearchLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Result>> {
        return tmdbLiveDataSource
    }
}