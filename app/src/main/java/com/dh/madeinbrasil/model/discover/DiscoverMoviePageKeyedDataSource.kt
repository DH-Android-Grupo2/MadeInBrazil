package com.dh.madeinbrasil.model.discover

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.database.MadeInBrazilDatabase
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.repository.HomeRepository
import com.dh.madeinbrasil.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.dh.madeinbrasil.model.upcoming.Result
import com.dh.madeinbrasil.utils.Constants.Paging.FIRST_PAGE
import com.dh.madeinbrasil.view.activity.MenuActivity
import com.dh.madeinbrasil.view.fragment.HomeFragment

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
                    if(MenuActivity.USER.genresSelected.isNotEmpty()) {
                        val handleList = mutableListOf<Result>()
                        MenuActivity.USER.genresSelected.forEach { gender ->
                            val handler = data.results.filter {
                                it.genreIds.contains(gender.toInt())
                            }
                            handler.forEach {
                                if(!handleList.contains(it)) {
                                    handleList.add(it)
                                }
                            }
                        }
                        data.results = handleList
                    }

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
                    var movies =  DiscoverMovieDB.getDiscover()

                    if(MenuActivity.USER.genresSelected.isNotEmpty()) {
                        val handleList = mutableListOf<Result>()
                        MenuActivity.USER.genresSelected.forEach { gender ->
                            val handler = movies.filter {
                                it.genreIds.contains(gender.toInt())
                            }
                            handler.forEach {
                                if(!handleList.contains(it)) {
                                    handleList.add(it)
                                }
                            }
                        }
                        movies = handleList
                    }
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
                    if(MenuActivity.USER.genresSelected.isNotEmpty()) {
                        val handleList = mutableListOf<Result>()
                        MenuActivity.USER.genresSelected.forEach { gender ->
                            val handler = data.results.filter {
                                it.genreIds.contains(gender.toInt())
                            }
                            handler.forEach {
                                if(!handleList.contains(it)) {
                                    handleList.add(it)
                                }
                            }
                        }
                        data.results = handleList
                    }

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
                    if(MenuActivity.USER.genresSelected.isNotEmpty()) {
                        val handleList = mutableListOf<Result>()
                        MenuActivity.USER.genresSelected.forEach { gender ->
                            val handler = data.results.filter {
                                it.genreIds.contains(gender.toInt())
                            }
                            handler.forEach {
                                if(!handleList.contains(it)) {
                                    handleList.add(it)
                                }
                            }
                        }
                        data.results = handleList
                    }

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