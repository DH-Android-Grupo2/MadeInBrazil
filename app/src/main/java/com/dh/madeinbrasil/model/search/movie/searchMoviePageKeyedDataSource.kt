package com.dh.madeinbrasil.model.search.movie

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.database.MadeInBrazilDatabase
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.model.upcoming.Result
import com.dh.madeinbrasil.repository.FilmsRepository
import com.dh.madeinbrasil.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class searchMoviePageKeyedDataSource (
        private val context : Context,
        var query:String) : PageKeyedDataSource<Int, Result>() {


    private val repository by lazy {
        FilmsRepository()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.searchMovies(Constants.Paging.FIRST_PAGE, query)) {
                is ResponseAPI.Success -> {
                    val data = response.let{
                        it.data as SearchMovie
                    }
                    data.results = data.results.filter { it.originalLanguage.equals("pt") }
                    data.results.forEach { result ->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let { string ->
                            result.backdropPath = string.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }


                    if (query.length > 3){

                    val searchMovieDB = MadeInBrazilDatabase.getDatabase(context).FilmsFragmentDao()
                    searchMovieDB.insertSearchMovies(data.results)
                    }




                    callback.onResult(data.results, null, Constants.Paging.FIRST_PAGE + 1)
                }
                is ResponseAPI.Error -> {


                    val subQuery = query.trim()
                    if (subQuery.isEmpty() || subQuery == "" ) {
                        callback.onResult(mutableListOf(), null, Constants.Paging.FIRST_PAGE + 1)
                    } else {

                        val searchMovie = MadeInBrazilDatabase.getDatabase(context).upcomingDao()
                        val stringQuery = StringBuilder()
                        stringQuery.append("%").append(query).append("%")
                        val movie = searchMovie.getSearchQueryMovie(stringQuery.toString())
                        callback.onResult(movie, null, Constants.Paging.FIRST_PAGE + 1)

                    }
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
            when (val response = repository.searchMovies(page,query)) {
                is ResponseAPI.Success -> {
                    val data = response.let{
                        it.data as SearchMovie
                    }
                    data.results = data.results.filter { it.originalLanguage.equals("pt") }
                    data.results.forEach { result ->
                        result.posterPath = result.posterPath?.getFullImagePath()
                        result.backdropPath?.let { string ->
                            result.backdropPath = string.getFullImagePath()
                        }.also {
                            result.backdropPath = result.posterPath
                        }
                    }

                    if (query.length > 3){

                        val searchMovieDB = MadeInBrazilDatabase.getDatabase(context).FilmsFragmentDao()
                        searchMovieDB.insertSearchMovies(data.results) }


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
            when (val response = repository.searchMovies(page,query)) {
                is ResponseAPI.Success -> {
                    val data = response.let{
                        it.data as SearchMovie
                    }
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


                    if (query.length > 3){

                        val searchMovieDB = MadeInBrazilDatabase.getDatabase(context).FilmsFragmentDao()
                        searchMovieDB.insertSearchMovies(data.results) }


                    callback.onResult(data.results, page - 1)
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), page - 1)
                }
            }
        }
    }
}
