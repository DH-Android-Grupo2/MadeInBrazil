package com.example.madeinbrasil.model.upcoming

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.repository.HomeRepository
import com.example.madeinbrasil.utils.Constants.Paging.FIRST_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UpcomingPageKeyedDataSource : PageKeyedDataSource<Int, Result>() {

    private val repository by lazy {
        HomeRepository()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        CoroutineScope(IO).launch {
            when(val response = repository.getUpcoming(FIRST_PAGE)) {
                is ResponseAPI.Success -> {
                    val data = response.data as Upcoming
                    data.results = data.results.filter { it.originalLanguage.equals("pt") }
                    data.results.forEach {result->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let{ string->
                            result.backdropPath = string.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }
                    callback.onResult(data.results, null, FIRST_PAGE + 1)
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), null, FIRST_PAGE + 1)
                }
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Result>
    ) {
        val page = params.key

        CoroutineScope(IO).launch {
            when(val response = repository.getUpcoming(page)) {
                is ResponseAPI.Success -> {
                    val data = response.data as Upcoming
                    data.results = data.results.filter { it.originalLanguage.equals("pt") }
                    data.results.forEach { result->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let{ string->
                            result.backdropPath = string.getFullImagePath()
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
        callback: LoadCallback<Int, Result>
    ) {
        val page = params.key

        CoroutineScope(IO).launch {
            when(val response = repository.getUpcoming(page)) {
                is ResponseAPI.Success -> {
                    val data = response.data as Upcoming
                    data.results = data.results.filter { it.originalLanguage.equals("pt") }
                    data.results.forEach {result->
                        result.posterPath?.let{ string->
                            result.posterPath = string.getFullImagePath()
                    }
                        result.backdropPath?.let{ string->
                            result.backdropPath = string.getFullImagePath()
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