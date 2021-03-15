package com.dh.madeinbrasil.model.discoverTV

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.database.MadeInBrazilDatabase
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.model.search.ResultSearch
import com.dh.madeinbrasil.repository.HomeRepository
import com.dh.madeinbrasil.utils.Constants
import com.dh.madeinbrasil.utils.Constants.Paging.FIRST_PAGE
import com.dh.madeinbrasil.view.activity.MenuActivity
import com.dh.madeinbrasil.view.fragment.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiscoverTvPageKeyedDataSource(
    private val context: Context
): PageKeyedDataSource<Int, ResultSearch>() {

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
                    data.results
                    if(MenuActivity.USER.genresSelected.isNotEmpty()) {
                        val handleList = mutableListOf<ResultSearch>()
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
                            result.backdropPath = result.backdropPath
                        }
                    }


                    val discoverTvDb = MadeInBrazilDatabase.getDatabase(context).discoverDao()
                    discoverTvDb.insertDiscover(data.results)

                    callback.onResult(data.results , null, Constants.Paging.FIRST_PAGE + 1)
                }
                is ResponseAPI.Error -> {

                    val discoverTvDb = MadeInBrazilDatabase.getDatabase(context).discoverDao()
                    var movies = discoverTvDb.getDiscover()

                    if(MenuActivity.USER.genresSelected.isNotEmpty()) {
                        val handleList = mutableListOf<ResultSearch>()
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
            callback: LoadCallback<Int, ResultSearch>
    ) {
        val page = params.key


        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getDiscoverTV(page,genreSelected)) {
                is ResponseAPI.Success -> {
                    val data = response.data as DiscoverTV
                    if(MenuActivity.USER.genresSelected.isNotEmpty()) {
                        val handleList = mutableListOf<ResultSearch>()
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
                            result.backdropPath= string.getFullImagePath()
                        }.also {
                            result.backdropPath= result.posterPath
                        }
                    }

                    val discoverTvDb = MadeInBrazilDatabase.getDatabase(context).discoverDao()
                    discoverTvDb.insertDiscover(data.results)

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
                    if(MenuActivity.USER.genresSelected.isNotEmpty()) {
                        val handleList = mutableListOf<ResultSearch>()
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
                    val discoverTvDb = MadeInBrazilDatabase.getDatabase(context).discoverDao()
                    discoverTvDb.insertDiscover(data.results)

                    callback.onResult(data.results, page - 1)
                }
                is ResponseAPI.Error -> {


                    callback.onResult(mutableListOf(), page - 1)
                }
            }
        }
    }




}


