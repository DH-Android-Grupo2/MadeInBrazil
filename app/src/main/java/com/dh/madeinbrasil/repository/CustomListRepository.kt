package com.dh.madeinbrasil.repository

import android.content.Context
import com.dh.madeinbrasil.api.firebase.FirebaseResponse
import com.dh.madeinbrasil.model.customLists.ListWithMedia
import com.dh.madeinbrasil.model.customLists.firebase.CustomList
import com.dh.madeinbrasil.model.customLists.firebase.Media
import com.dh.madeinbrasil.utils.Constants.CustomLists.CUSTOM_LIST_MEDIA_ITEM
import com.dh.madeinbrasil.utils.Constants.CustomLists.ERROR_ADD_ITEM
import com.dh.madeinbrasil.utils.Constants.CustomLists.ERROR_CREATE_LIST
import com.dh.madeinbrasil.utils.Constants.CustomLists.ERROR_DELETE_ITEMS
import com.dh.madeinbrasil.utils.Constants.CustomLists.ERROR_GET_LISTS
import com.dh.madeinbrasil.utils.Constants.CustomLists.ERROR_UPDATE_LIST
import com.dh.madeinbrasil.utils.Constants.CustomLists.ITEM_ADICIONADO
import com.dh.madeinbrasil.utils.Constants.CustomLists.ITEM_JA_ADICIONADO
import com.dh.madeinbrasil.utils.Constants.CustomLists.LISTS
import com.dh.madeinbrasil.utils.Constants.CustomLists.MOVIE
import com.dh.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
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
            auth.currentUser?.let { firebaseUser ->
                val user = db.collection(DATABASE_USERS).document(firebaseUser.uid).get().await()

                val listsRef = db.collection(LISTS)

                db.runTransaction {
                    val snapshot = it.get(listsRef.document())

                    val docRef = listsRef.document(snapshot.id)

                    it.set(docRef, customList.apply {
                        id = snapshot.id
                        userId = firebaseUser.uid
                        ownerName = user.getString("name") ?: ""
                    })
                }.await()
            }

                if (mediaList.isNotEmpty()) {
                    val batch = db.batch()
                    val customListMediaItemRef = db.collection(CUSTOM_LIST_MEDIA_ITEM)
                    mediaList.forEach {
                        val docRef = customListMediaItemRef.document(it.id)
                        batch.set(docRef, it)
                    }

                    batch.commit().addOnCompleteListener {
                        if (it.isSuccessful)
                            resp = FirebaseResponse.OnSucess("")
                        else
                            resp = FirebaseResponse.OnFailure(it.exception?.localizedMessage
                                    ?: ERROR_CREATE_LIST)
                    }.await()
                    return resp
                }
                resp = FirebaseResponse.OnSucess("")
            } catch (e: Exception) {
            resp = FirebaseResponse.OnFailure(e.localizedMessage ?: ERROR_CREATE_LIST)
        }

        return resp

    }

    suspend fun getUserExceptionLists(): FirebaseResponse {
        lateinit var resp: FirebaseResponse

        try {
            auth.currentUser?.let {
                db.collection(LISTS).whereEqualTo("userId", it.uid).orderBy("name").get()
                        .addOnCompleteListener {
                            if (it.isSuccessful)
                                resp = FirebaseResponse.OnSucess(it.result.toObjects(CustomList::class.java))
                            else
                                resp = FirebaseResponse.OnFailure(it.exception?.localizedMessage ?: ERROR_GET_LISTS)
                        }.await()
            }
        } catch (e: Exception) {
            resp = FirebaseResponse.OnFailure(e.localizedMessage ?: ERROR_GET_LISTS)
        }
        return resp
    }

    suspend fun getListWithMedia(all: Boolean): FirebaseResponse {
        lateinit var resp: FirebaseResponse
        var customLists: List<CustomList>? = null
        try {
            lateinit var listQuery: Query
            if(all)
                listQuery = db.collection(LISTS).orderBy("name")
            else
                auth.currentUser?.let {
                    listQuery = db.collection(LISTS).whereEqualTo("userId", it.uid).orderBy("name")
                }

            listQuery.get()
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                            customLists = it.result.toObjects(CustomList::class.java)
                        else
                            resp = FirebaseResponse.OnFailure(it.exception?.localizedMessage ?: ERROR_GET_LISTS)

                    }.await()

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

    suspend fun addItemtoList(list: ListWithMedia, showType: String): FirebaseResponse {
        lateinit var resp: FirebaseResponse
        try {
            val listRef = db.collection(LISTS).document(list.list.id)
            val snapshot = listRef.get().await()
            var items: MutableList<String>

            if(showType.equals(MOVIE)) {

                items = snapshot.get("movies") as MutableList<String>
                if(items.contains(list.mediaList[0].id))
                    return FirebaseResponse.OnSucess(ITEM_JA_ADICIONADO)
                items.add(list.mediaList[0].id)

            } else {
                items = snapshot.get("series") as MutableList<String>
                if(items.contains(list.mediaList[0].id))
                    return FirebaseResponse.OnSucess(ITEM_JA_ADICIONADO)
                items.add(list.mediaList[0].id)
            }

            val customListItemRef = db.collection(CUSTOM_LIST_MEDIA_ITEM).document(list.mediaList[0].id)

            db.runBatch {
                val field = if(showType.equals(MOVIE)) "movies" else "series"

                it.update(listRef, hashMapOf<String, Any>(
                        field to items
                ))

                it.set(customListItemRef, list.mediaList[0])

            }.addOnCompleteListener {
                if(it.isSuccessful)
                    resp = FirebaseResponse.OnSucess(ITEM_ADICIONADO)
                else
                    resp = FirebaseResponse.OnFailure(it.exception?.localizedMessage ?: ERROR_ADD_ITEM)
            }.await()
        } catch (e: Exception) {
            resp = FirebaseResponse.OnFailure(e.localizedMessage ?: ERROR_ADD_ITEM)
        }

        return resp
    }

    suspend fun resetListMedia(listId: String, moviesId: List<String>, seriesId: List<String>): FirebaseResponse {
        lateinit var resp: FirebaseResponse

        try {
            db.collection(LISTS).document(listId)
                    .update(hashMapOf<String, Any>(
                            "movies" to moviesId,
                            "series" to seriesId
                        )).addOnCompleteListener {
                            if(it.isSuccessful)
                                resp = FirebaseResponse.OnSucess("")
                            else
                                resp = FirebaseResponse.OnFailure(it.exception?.localizedMessage ?: ERROR_DELETE_ITEMS)
                    }.await()
        } catch (e: Exception) {
            resp = FirebaseResponse.OnFailure(e.localizedMessage ?: ERROR_DELETE_ITEMS)
        }

        return resp
    }

    suspend fun updateList(list: ListWithMedia): FirebaseResponse {
        lateinit var resp: FirebaseResponse

        try {
            val listRef = db.collection(LISTS).document(list.list.id)
            val customListITemsRef = db.collection(CUSTOM_LIST_MEDIA_ITEM)
            db.runBatch { batch ->
                batch.update(listRef, hashMapOf<String, Any>(
                        "name" to list.list.name,
                        "description" to list.list.description.toString(),
                        "movies" to list.list.movies,
                        "series" to list.list.series
                ))

                if (list.mediaList.isNotEmpty())
                    list.mediaList.forEach {
                        batch.set(customListITemsRef.document(it.id), it)
                    }
            }.addOnCompleteListener {
                if (it.isSuccessful)
                    resp = FirebaseResponse.OnSucess("")
                else
                    resp = FirebaseResponse.OnFailure(it.exception?.localizedMessage
                            ?: ERROR_UPDATE_LIST)
            }.await()
        } catch (e: Exception) {
            resp = FirebaseResponse.OnFailure(e.localizedMessage ?: ERROR_UPDATE_LIST)
        }

        return resp
    }

    suspend fun deleteLists(selectedLists: List<String>): FirebaseResponse {
        lateinit var resp: FirebaseResponse
        try {
            val listsRef = db.collection(LISTS)
            val batch = db.batch()
            selectedLists.forEach {
                val listRef = listsRef.document(it)
                batch.delete(listRef)
            }
            batch.commit().addOnCompleteListener {
                if(it.isSuccessful)
                    resp = FirebaseResponse.OnSucess("")
                else
                    resp = FirebaseResponse.OnFailure(it.exception?.localizedMessage ?: ERROR_DELETE_ITEMS)
            }.await()
        } catch(e: Exception) {
            resp = FirebaseResponse.OnFailure(e.localizedMessage ?: ERROR_DELETE_ITEMS)
        }

        return resp
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