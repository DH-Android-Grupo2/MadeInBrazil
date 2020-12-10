package com.example.madeinbrasil.model.discoverTV

import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.gender.Genre
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.repository.HomeRepository
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.Paging.FIRST_PAGE
import com.example.madeinbrasil.view.fragment.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class DiscoverTvPageKeyedDataSource(): PageKeyedDataSource<Int, Result>() {


    val genre = HomeFragment.genre.toString()
    private  val  repository by lazy {
        HomeRepository()
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Result>
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscoverTV(FIRST_PAGE, "18")) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverTV
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
            callback: LoadCallback<Int, Result>
    ) {
        val page = params.key


        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscoverTV(page, "18")) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverTV
                    data.results.forEach { result ->
                        result.poster_path = result.poster_path?.getFullImagePath()
                        result.backdrop_path?.let { string ->
                            result.backdrop_path= string.getFullImagePath()
                        }.also {
                            result.backdrop_path= result.poster_path
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



        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscoverTV(page, "18")) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverTV
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


