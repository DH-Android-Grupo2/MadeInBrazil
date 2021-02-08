package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.firebase.FirebaseResponse
import com.example.madeinbrasil.model.customLists.ListWithMedia
import com.example.madeinbrasil.model.customLists.firebase.CustomList
import com.example.madeinbrasil.model.customLists.firebase.Media
import com.example.madeinbrasil.utils.Constants.CustomLists.CUSTOM_LIST_MEDIA_ITEM
import com.example.madeinbrasil.utils.Constants.CustomLists.ERROR_CREATE_LIST
import com.example.madeinbrasil.utils.Constants.CustomLists.ERROR_GET_LISTS
import com.example.madeinbrasil.utils.Constants.CustomLists.LISTS
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class CustomListRepository(context: Context? = null) {

    private val auth by lazy {
        Firebase.auth
    }

    private val db by lazy {
        Firebase.firestore
    }


    suspend fun createList(customList: CustomList, mediaList: List<Media>): FirebaseResponse {

        lateinit var resp: FirebaseResponse

        try {
            auth.currentUser?.let {
                val listSize = getUserListsSize(it.uid)
                val user = db.collection(DATABASE_USERS).document(it.uid).get().await()
                db.collection(LISTS).document("${auth.currentUser?.uid}${listSize + 1}")
                    .set(customList.apply {
                        id = "${auth.currentUser?.uid}${listSize + 1}"
                        userId = it.uid
                        ownerName = user.getString("name") ?: ""
                    }).await()

                val batch = db.batch()
                val customListMediaItemRef = db.collection(CUSTOM_LIST_MEDIA_ITEM)
                mediaList.forEach {
                    val docRef = customListMediaItemRef.document(it.id)
                    batch.set(docRef, it)
                }

                batch.commit().addOnCompleteListener {
                    if(it.isSuccessful)
                        resp = FirebaseResponse.OnSucess("")
                    else
                        resp = FirebaseResponse.OnFailure(it.exception?.localizedMessage ?: ERROR_CREATE_LIST)
                }.await()
            }
        } catch (e: Exception) {
            resp = FirebaseResponse.OnFailure(e.localizedMessage ?: ERROR_CREATE_LIST)
        }

        return resp

    }

    suspend fun getListWithMedia(): FirebaseResponse {
        lateinit var resp: FirebaseResponse
        var customLists: List<CustomList>? = null
        try {
            auth.currentUser?.let {
                db.collection(LISTS).whereEqualTo("userId", it.uid).get()
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                            customLists = it.result.toObjects(CustomList::class.java)
                        else
                            throw Exception(ERROR_GET_LISTS)
                    }.await()
            }
        val listWithMedia = mutableListOf<ListWithMedia>()
        val customListsItemRef =  db.collection(CUSTOM_LIST_MEDIA_ITEM)
        customLists?.forEach { cl ->

            var movies: List<Media>? = null
            var series: List<Media>? = null

            customLists?.let {
                cl.movies?.let { moviesId ->
                    if (moviesId.isNotEmpty())
                        customListsItemRef.whereIn("id", moviesId.toMutableList()).get()
                                .addOnCompleteListener {
                                    if (it.isSuccessful)
                                        movies = it.result.toObjects(Media::class.java)
                                }.await()
                }

                cl.series?.let { seriesId ->
                    if (seriesId.isNotEmpty())
                    customListsItemRef.whereIn("id", seriesId.toMutableList()).get()
                            .addOnCompleteListener {
                                if (it.isSuccessful)
                                    series = it.result.toObjects(Media::class.java)
                            }.await()
                }
            }

            val items = mutableListOf<Media>()

            movies?.let { list ->
                if (list.isNotEmpty())
                    list.forEach {
                        items.add(it)
                    }
            }
            series?.let { list ->
                if (list.isNotEmpty())
                    list.forEach {
                        items.add(it)
                    }
            }

            listWithMedia.add(ListWithMedia(cl, items))

        }
            resp = FirebaseResponse.OnSucess(listWithMedia)
        } catch (e: Exception) {
            resp = FirebaseResponse.OnFailure(e.localizedMessage ?: ERROR_GET_LISTS)
        }

        return resp
    }

     private suspend fun getUserListsSize(id: String): Int {
        var size = 0
        db.collection("lists").whereEqualTo("userId", id).get()
            .addOnSuccessListener {
                size = it.size()
            }.await()
        return size
    }

//    private val customListDao by lazy {
//        MadeInBrazilDatabase.getDatabase(context).customListDao()
//    }
//
//    private val listMovieItemDao by lazy {
//        MadeInBrazilDatabase.getDatabase(context).listMovieItemDao()
//    }
//
//    private val listSerieItemDao by lazy {
//        MadeInBrazilDatabase.getDatabase(context).listSerieItemDao()
//    }

//    suspend fun createList(list: ListWithMedia) {
//
//        val listId = customListDao.createList(list.list)
//
//        listMovieItemDao.saveMovies(list.movies)
//
//        val movieCrossRef: MutableList<ListMovieCrossRef> = mutableListOf()
//
//        list.movies.forEach {
//            movieCrossRef.add(ListMovieCrossRef(listId, it.movieId))
//        }
//
//        customListDao.saveCustomListCrossrefMovie(movieCrossRef)
//
//        listSerieItemDao.saveSeries(list.series)
//
//        val serieCrossRef: MutableList<ListSerieCrossRef> = mutableListOf()
//
//        list.series.forEach {
//            serieCrossRef.add(ListSerieCrossRef(listId, it.serieId))
//        }
//
//        customListDao.saveCustomListCrossrefSerie(serieCrossRef)
//    }
//
//    suspend fun getLists(): List<ListWithMedia> {
//        return customListDao.getListWithMedia()
//    }
//
//    suspend fun getListUni(id: Long): ListWithMedia {
//        return customListDao.getListUni(id)
//    }
//
//    suspend fun getCustomLists(): List<CustomList> {
//        return customListDao.getCustomLists()
//    }
//
//    suspend fun getCustomListMovieIds(listId: Long): List<Long> {
//        return listMovieItemDao.getCustomListMovieIds(listId)
//    }
//
//    suspend fun deleteMoviesFromList(items: List<ListMovieCrossRef>) {
//        return customListDao.deleteMoviesFromList(items)
//    }
//
//    suspend fun deleteSeriesFromList(items: List<ListSerieCrossRef>) {
//        return customListDao.deleteSeriesFromList(items)
//    }

}