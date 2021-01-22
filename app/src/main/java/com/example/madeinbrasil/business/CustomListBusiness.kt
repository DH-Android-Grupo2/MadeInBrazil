package com.example.madeinbrasil.business

import android.content.Context
import com.example.madeinbrasil.model.customLists.*
import com.example.madeinbrasil.model.customLists.relation.ListWithMedia
import com.example.madeinbrasil.repository.CustomListRepository

class CustomListBusiness(context: Context) {

    private val customListRepository by lazy {
        CustomListRepository(context)
    }

    suspend fun createList(list: ListWithMedia) {
        customListRepository.createList(list)
    }

    suspend fun getLists(): List<ListWithMedia> {
       return customListRepository.getLists()
    }

    suspend fun getListsUni(): List<ListWithMediaUni> {
        val lists = customListRepository.getLists()
        val uniLists: MutableList<ListWithMediaUni> = mutableListOf()

        lists.forEach { list ->
            val media: MutableList<ListMediaItem> = mutableListOf()
            list.movies.forEach {
                media.add(ListMediaItem(it.movieId, it.title, it.backdropPath, it.originalTitle))
            }
            list.series.forEach {
                media.add(ListMediaItem(it.serieId, it.title, it.backdropPath, it.originalTitle))
            }

            uniLists.add(ListWithMediaUni(list.list, media))
        }

        return uniLists
    }

    suspend fun getListUni(id: Long): ListWithMediaUni {
        val list = customListRepository.getListUni(id)
        val media: MutableList<ListMediaItem> = mutableListOf()

        list.movies.forEach {
            media.add(ListMediaItem(it.movieId, it.title, it.backdropPath, it.originalTitle))
        }

        list.series.forEach {
            media.add(ListMediaItem(it.serieId, it.title, it.backdropPath, it.originalTitle))
        }

        return ListWithMediaUni(list.list, media)
    }

    suspend fun getCustomLists(): List<CustomList> {
        return customListRepository.getCustomLists()
    }

    suspend fun getCustomListMovieIds(listId: Long): List<Long> {
        return customListRepository.getCustomListMovieIds(listId)
    }

    suspend fun deleteMoviesFromList(id: Long, listId: List<Long>) {
        val items = mutableListOf<ListMovieCrossRef>()
        listId.forEach {
            items.add(ListMovieCrossRef(id, it))
        }
        return customListRepository.deleteMoviesFromList(items)
    }

    suspend fun deleteSeriesFromList(id: Long, listId: List<Long>) {
        val items = mutableListOf<ListSerieCrossRef>()
        listId.forEach {
            items.add(ListSerieCrossRef(id, it))
        }
        return customListRepository.deleteSeriesFromList(items)
    }
}