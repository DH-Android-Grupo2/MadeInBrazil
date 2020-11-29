package com.example.madeinbrasil.model.search.serie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.model.search.Result

class SearchSerieDataSourceFactory(var query: String): DataSource.Factory<Int, Result>() {

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Result>>()

    override fun create(): DataSource<Int, Result> {
        val tmdbDataSource = SearchSeriePageKeyedDataSource(query)

        tmdbLiveDataSource.postValue(tmdbDataSource)
        return tmdbDataSource
    }

    fun getSearchSerieLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Result>> {
        return tmdbLiveDataSource
    }

}