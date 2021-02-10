package com.example.madeinbrasil.business

import android.content.Context
import com.example.madeinbrasil.api.firebase.FirebaseResponse
import com.example.madeinbrasil.model.customLists.firebase.CustomList
import com.example.madeinbrasil.model.customLists.firebase.Media
import com.example.madeinbrasil.repository.CustomListRepository
import com.example.madeinbrasil.utils.Constants.CustomLists.DELETE_TASK_OK

class CustomListBusiness(context: Context? = null) {

    private val customListRepository by lazy {
        CustomListRepository()
    }

    suspend fun createList(customList: CustomList, mediaList: List<Media>): FirebaseResponse {
        return customListRepository.createList(customList, mediaList)
    }

    suspend fun getListsWithMedia(): FirebaseResponse {
        return customListRepository.getListWithMedia()
    }

    suspend fun resetMediaList(listId: String, moviesId: List<String>, seriesId: List<String>): FirebaseResponse {
        return when (val response = customListRepository.resetListMedia(listId, moviesId, seriesId)) {
            is FirebaseResponse.OnSucess ->
                FirebaseResponse.OnSucess(DELETE_TASK_OK)
            is FirebaseResponse.OnFailure ->
                response
        }
    }

    suspend fun deleteLists(selectedLists: List<String>): FirebaseResponse {
        return when (val response = customListRepository.deleteLists(selectedLists)) {
            is FirebaseResponse.OnSucess ->
                FirebaseResponse.OnSucess(DELETE_TASK_OK)
            is FirebaseResponse.OnFailure ->
                response
        }
    }
//
//    suspend fun createList(list: ListWithMedia) {
//        customListRepository.createList(list)
//    }
//
//    suspend fun getLists(): List<ListWithMedia> {
//       return customListRepository.getLists()
//    }
//
//    suspend fun getListsUni(): List<ListWithMediaUni> {
//        val lists = customListRepository.getLists()
//        val uniLists: MutableList<ListWithMediaUni> = mutableListOf()
//
//        lists.forEach { list ->
//            val media: MutableList<ListMediaItem> = mutableListOf()
//            list.movies.forEach {
//                media.add(ListMediaItem(it.movieId, it.title, it.backdropPath, it.originalTitle))
//            }
//            list.series.forEach {
//                media.add(ListMediaItem(it.serieId, it.title, it.backdropPath, it.originalTitle))
//            }
//
//            uniLists.add(ListWithMediaUni(list.list, media))
//        }
//
//        return uniLists
//    }
//
//    suspend fun getListUni(id: Long): ListWithMediaUni {
//        val list = customListRepository.getListUni(id)
//        val media: MutableList<ListMediaItem> = mutableListOf()
//
//        list.movies.forEach {
//            media.add(ListMediaItem(it.movieId, it.title, it.backdropPath, it.originalTitle))
//        }
//
//        list.series.forEach {
//            media.add(ListMediaItem(it.serieId, it.title, it.backdropPath, it.originalTitle))
//        }
//
//        return ListWithMediaUni(list.list, media)
//    }
//
//    suspend fun getCustomLists(): List<CustomList> {
//        return customListRepository.getCustomLists()
//    }
//
//    suspend fun getCustomListMovieIds(listId: Long): List<Long> {
//        return customListRepository.getCustomListMovieIds(listId)
//    }
//
//    suspend fun deleteMoviesFromList(id: Long, listId: List<Long>) {
//        val items = mutableListOf<ListMovieCrossRef>()
//        listId.forEach {
//            items.add(ListMovieCrossRef(id, it))
//        }
//        return customListRepository.deleteMoviesFromList(items)
//    }
//
//    suspend fun deleteSeriesFromList(id: Long, listId: List<Long>) {
//        val items = mutableListOf<ListSerieCrossRef>()
//        listId.forEach {
//            items.add(ListSerieCrossRef(id, it))
//        }
//        return customListRepository.deleteSeriesFromList(items)
//    }
}