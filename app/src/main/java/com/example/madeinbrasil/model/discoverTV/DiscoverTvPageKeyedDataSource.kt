package com.example.madeinbrasil.model.discoverTV

import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.gender.Genre
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.repository.HomeRepository
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.Paging.FIRST_PAGE
import com.example.madeinbrasil.view.fragment.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class DiscoverTvPageKeyedDataSource(): PageKeyedDataSource<Int, ResultSearch>() {

    var vazio = ""
    var genreSelected = ""

    val teste = HomeFragment.genre?.Selected?.onEach {

        genreSelected = it + genreSelected
    }

    private  val  repository by lazy {
        HomeRepository()
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, ResultSearch>
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscoverTV(FIRST_PAGE, genreSelected)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverTV
                    data.results.forEach { result ->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let { string ->
                            result.backdropPath = string.getFullImagePath()
                        }.also {
                            result.backdropPath = result.backdropPath
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
            callback: LoadCallback<Int, ResultSearch>
    ) {
        val page = params.key


        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscoverTV(page,genreSelected)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverTV
                    data.results.forEach { result ->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let { string ->
                            result.backdropPath= string.getFullImagePath()
                        }.also {
                            result.backdropPath= result.posterPath
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



        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscoverTV(page, genreSelected)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverTV
                    data.results.forEach { result ->
                        result.posterPath?.let { string ->
                            result.posterPath = string.getFullImagePath()
                        }
                        result.backdropPath?.let { string ->
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


