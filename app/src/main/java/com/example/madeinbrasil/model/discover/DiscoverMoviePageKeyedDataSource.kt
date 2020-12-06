package com.example.madeinbrasil.model.discover

import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.repository.HomeRepository
import com.example.madeinbrasil.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiscoverMoviePageKeyedDataSource : PageKeyedDataSource<Int, Result>() {

    private val repository by lazy {
        HomeRepository()
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Result>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscover(Constants.Paging.FIRST_PAGE, 27)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverMovie
                    data.results = data.results.filter { it.original_language.equals("pt") }
                    data.results.forEach { result ->
                        result.poster_path = result.poster_path?.getFullImagePath()
                        result.backdrop_path?.let { string ->
                            result.backdrop_path = string.getFullImagePath()
                        }.also {
                            result.backdrop_path = result.poster_path
                        }
                    }

                    callback.onResult(data.results , null, Constants.Paging.FIRST_PAGE + 1)
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), null, Constants.Paging.FIRST_PAGE + 1)
                }
            }
        }
    }

    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, com.example.madeinbrasil.model.discover.Result>
    ) {
        val page = params.key

        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscover(page, 27)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverMovie
                    data.results = data.results.filter { it.original_language.equals("pt") }
                    data.results.forEach { result ->
                        result.poster_path = result.poster_path?.getFullImagePath()
                        result.backdrop_path?.let { string ->
                            result.backdrop_path = string.getFullImagePath()
                        }.also {
                            result.backdrop_path = result.poster_path
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
            callback: LoadCallback<Int, com.example.madeinbrasil.model.discover.Result>
    ) {
        val page = params.key

        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscover(page, 27)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverMovie
                    data.results = data.results.filter { it.original_language.equals("pt") }
                    data.results.forEach { result ->
                        result.poster_path?.let { string ->
                            result.poster_path = string.getFullImagePath()
                        }
                        result.backdrop_path?.let { string ->
                            result.backdrop_path = string.getFullImagePath()
                        }.also {
                            result.backdrop_path = result.poster_path
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