package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.firebase.FirebaseResponse
import com.example.madeinbrasil.model.customLists.firebase.CustomList
import com.example.madeinbrasil.model.customLists.firebase.Media
import com.example.madeinbrasil.utils.Constants.CustomLists.ERROR_CREATE_LIST
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
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
                db.collection("lists").document("${auth.currentUser?.uid}${listSize + 1}")
                    .set(customList.apply {
                        id = "${auth.currentUser?.uid}${listSize + 1}"
                        userId = it.uid
                        ownerName = user.getString("name") ?: ""
                    }).await()

                val batch = db.batch()
                val customListMediaItemRef = db.collection("customListMediaItem")
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

    suspend fun getUserListsSize(id: String): Int {
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