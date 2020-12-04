package com.example.madeinbrasil.model.search.serie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.model.search.ResultSearch

class SearchSerieDataSourceFactory(var query: String): DataSource.Factory<Int, ResultSearch>() {

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, ResultSearch>>()


    override fun create(): DataSource<Int, ResultSearch> {
        val tmdbDataSource = SearchSeriePageKeyedDataSource(query)

        tmdbLiveDataSource.postValue(tmdbDataSource)
        return tmdbDataSource
    }

    fun getSearchSerieLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, ResultSearch>> {
        return tmdbLiveDataSource
    }

}