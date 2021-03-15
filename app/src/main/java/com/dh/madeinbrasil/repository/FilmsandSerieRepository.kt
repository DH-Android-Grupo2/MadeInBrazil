package com.dh.madeinbrasil.repository

import com.dh.madeinbrasil.utils.Constants
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FilmsandSerieRepository {
    private val midiaFirebase by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_MIDIA)
    }
    private val castFirebase by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_CAST)
    }
    private val seasonFirebase by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_SEASON)
    }
    private val genreFirebase by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_GENRE)
    }

    suspend fun getMidia(id: Int): DocumentSnapshot {
        return midiaFirebase.document("$id").get().await()
    }

    suspend fun getCast(): QuerySnapshot {
        return castFirebase.get().await()
    }

    suspend fun getSeason(): QuerySnapshot {
        return seasonFirebase.get().await()
    }

    suspend fun getGenre(): QuerySnapshot {
        return genreFirebase.get().await()
    }
}