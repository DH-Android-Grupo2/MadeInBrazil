package com.dh.madeinbrasil.model.nowPlaying

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.database.MadeInBrazilDatabase
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.model.upcoming.Result
import com.dh.madeinbrasil.repository.HomeRepository
import com.dh.madeinbrasil.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NowPlayingPageKeyedDataSource(
    private val context: Context
) : PageKeyedDataSource<Int, Result>() {

    private val repository by lazy {
        HomeRepository()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getNowPlaying(Constants.Paging.FIRST_PAGE)) {
                is ResponseAPI.Success -> {
                    val data = response.data as NowPlaying
                    data.results = data.results.filter { it.originalLanguage.equals("pt") }
                    data.results.forEach { result ->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let { string ->
                            result.backdropPath = string.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }

                    data.results.forEach {
                        it.type = 1
                    }


                    val nowPlayingDB = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                    nowPlayingDB.insertUpcoming(data.results)
                    callback.onResult(data.results, null, Constants.Paging.FIRST_PAGE + 1)
                }
                is ResponseAPI.Error -> {
                    val upcomingDB = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                    val movies = upcomingDB.getNowPlaying()
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
            when (val response = repository.getNowPlaying(page)) {
                is ResponseAPI.Success -> {
                    val data = response.data as NowPlaying
                    data.results = data.results.filter { it.originalLanguage.equals("pt") }
                    data.results.forEach { result ->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let { string ->
                            result.backdropPath = string.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }

                    data.results.forEach {
                        it.type = 1
                    }

                    val nowPlaying = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                    nowPlaying.insertUpcoming(data.results)
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
            when (val response = repository.getNowPlaying(page)) {
                is ResponseAPI.Success -> {
                    val data = response.data as NowPlaying
                    data.results = data.results.filter { it.originalLanguage.equals("pt") }
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
                        it.type = 1
                    }

                    val nowPlayingDB = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                    nowPlayingDB.insertUpcoming(data.results)

                    callback.onResult(data.results, page - 1)
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), page - 1)
                }
            }
        }
    }
}
