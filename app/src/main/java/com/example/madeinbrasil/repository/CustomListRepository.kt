package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.model.customLists.*
import com.example.madeinbrasil.model.customLists.relation.ListWithMedia

class CustomListRepository(context: Context) {

    private val customListDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).customListDao()
    }

    private val listMovieItemDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).listMovieItemDao()
    }

    private val listSerieItemDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).listSerieItemDao()
    }

    suspend fun createList(list: ListWithMedia) {

        val listId = customListDao.createList(list.list)

        listMovieItemDao.saveMovies(list.movies)

        val movieCrossRef: MutableList<ListMovieCrossRef> = mutableListOf()

        list.movies.forEach {
            movieCrossRef.add(ListMovieCrossRef(listId, it.movieId))
        }

        customListDao.saveCustomListCrossrefMovie(movieCrossRef)

        listSerieItemDao.saveSeries(list.series)

        val serieCrossRef: MutableList<ListSerieCrossRef> = mutableListOf()

        list.series.forEach {
            serieCrossRef.add(ListSerieCrossRef(listId, it.serieId))
        }

        customListDao.saveCustomListCrossrefSerie(serieCrossRef)
    }

    suspend fun getLists(): List<ListWithMedia> {
        return customListDao.getListWithMedia()
    }

    suspend fun getListUni(id: Long): ListWithMedia {
        return customListDao.getListUni(id)
    }

    suspend fun getCustomLists(): List<CustomList> {
        return customListDao.getCustomLists()
    }

    suspend fun getCustomListMovieIds(listId: Long): List<Long> {
        return listMovieItemDao.getCustomListMovieIds(listId)
    }

}