package com.example.madeinbrasil.model.discover

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.repository.HomeRepository
import com.example.madeinbrasil.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants.Paging.FIRST_PAGE
import com.example.madeinbrasil.view.fragment.HomeFragment

class DiscoverMoviePageKeyedDataSource(
    private val context: Context
) : PageKeyedDataSource<Int, Result>() {



    var genreFinish = ""

    val teste = HomeFragment.genre?.Selected?.onEach {
        genreFinish = it + genreFinish
    }




    private val repository by lazy {
        HomeRepository()
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Result>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscover(FIRST_PAGE, genreFinish)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverMovie
                    data.results.forEach { result ->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let { string ->
                            result.backdropPath = string.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }

                    data.results.forEach {
                        it.type = 3
                    }
                    val DiscoverMovieDB = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                    DiscoverMovieDB.insertUpcoming(data.results)

                    callback.onResult(data.results , null, Constants.Paging.FIRST_PAGE + 1)
                }
                is ResponseAPI.Error -> {
                    val DiscoverMovieDB = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                    val movies =  DiscoverMovieDB.getDiscover()
                    callback.onResult(movies, null, Constants.Paging.FIRST_PAGE + 1)
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
            when (val response = repository.getDiscover(page, genreFinish)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverMovie
                    data.results.forEach { result ->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let { string ->
                            result.backdropPath = string.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }
                    data.results.forEach {
                        it.type = 3
                    }
                    val DiscoverMovieDB = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                    DiscoverMovieDB.insertUpcoming(data.results)

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
            when (val response = repository.getDiscover(page, genreFinish)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverMovie
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

                    data.results.forEach {
                        it.type = 3
                    }

                    val DiscoverMovieDB = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                    DiscoverMovieDB.insertUpcoming(data.results)

                    callback.onResult(data.results, page - 1)
                }
                is ResponseAPI.Error -> {


                    callback.onResult(mutableListOf(), page - 1)
                }
            }
        }
    }



}