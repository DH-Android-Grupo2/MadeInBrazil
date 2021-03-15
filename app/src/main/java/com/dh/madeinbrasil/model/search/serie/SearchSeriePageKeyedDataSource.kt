package com.dh.madeinbrasil.model.search.serie

import android.content.Context
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.database.MadeInBrazilDatabase
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.model.search.ResultSearch
import com.dh.madeinbrasil.repository.SerieRepository
import com.dh.madeinbrasil.utils.Constants.Paging.FIRST_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class SearchSeriePageKeyedDataSource(
        private val context: Context,
        var query: String): PageKeyedDataSource<Int, ResultSearch>() {



    private val repository by lazy {
        SerieRepository()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultSearch>
    ) {
        CoroutineScope(IO).launch {
            when(val response = repository.searchSerie(FIRST_PAGE, query)) {
                is ResponseAPI.Success -> {
                    val data = response.data as SearchSeries

                    data.results = data.results.filter { it.originCountry.contains("BR") }
                    data.results.forEach {result ->
                        result.originCountry.forEach {string ->
                            string.filter { it.equals("BR") }
                        }
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath.let { string ->
                            result.backdropPath = string?.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }

                    data.results.forEach {
                        it.type = 1
                    }

                    if(query.length > 3){

                    val searchTVDB = MadeInBrazilDatabase.getDatabase(context).discoverDao()
                    searchTVDB.insertSearchTV(data.results)
                    }


                    callback.onResult(data.results, null, FIRST_PAGE + 1)
                }
                is ResponseAPI.Error -> {

                    val subQuery = query.trim()
                    Log.d("TAGQuery",query)
                    if (subQuery.isEmpty() || subQuery == "" ) {
                        callback.onResult(mutableListOf(), null, FIRST_PAGE + 1)
                    } else {
                        val searchTV = MadeInBrazilDatabase.getDatabase(context).discoverDao()
                        val stringQuery = StringBuilder()
                        stringQuery.append("%").append(query).append("%")
                        val tv =  searchTV.getSearchQueryTV(stringQuery.toString())
                        callback.onResult(tv, null, FIRST_PAGE + 1)
                    }
                }

            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultSearch>) {
        val page = params.key

        CoroutineScope(IO).launch {
            when(val response = repository.searchSerie(page, query)) {
                is ResponseAPI.Success -> {
                    val data = response.data as SearchSeries
//                    var safe: List<Result>
                    data.results = data.results.filter  { it.originCountry.contains("BR") }
                    data.results.forEach {result ->
                        result.originCountry.forEach {string ->
                            string.filter { it.equals("BR") }
                        }
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath.let { string ->
                            result.backdropPath = string?.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }
                    callback.onResult(data.results, page + 1)
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), page + 1)
                }
            }
        }
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ResultSearch>
    ) {
        val page = params.key

        CoroutineScope(IO).launch {
            when(val response = repository.searchSerie(page, query)) {
                is ResponseAPI.Success -> {
                    val data = response.data as SearchSeries

                    data.results = data.results.filter  { it.originCountry.contains("BR") }
                    data.results.forEach {result ->
                        result.originCountry.forEach {string ->
                            string.filter { it.equals("BR") }
                        }
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath.let { string ->
                            result.backdropPath = string?.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }
                    callback.onResult(data.results, page - 1)
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), page - 1)
                }
            }
        }
    }
}