package com.example.madeinbrasil.repository

import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_CAST
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_GENRE
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_MIDIA
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_SEASON
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class MenuRepository {
    private val userFirebase by lazy {
        Firebase.firestore.collection(DATABASE_USERS).document(auth.currentUser?.uid ?: "")
    }
    private val midiaFirebase by lazy {
        Firebase.firestore.collection(DATABASE_MIDIA)
    }
    private val genreFirebase by lazy {
        Firebase.firestore.collection(DATABASE_GENRE)
    }
    private val auth by lazy {
        Firebase.auth
    }

    suspend fun getUser(): DocumentSnapshot {
       return userFirebase.get().await()
    }

    suspend fun getMidia(id: Int): DocumentSnapshot? {
        return midiaFirebase.document("$id").get().await()
    }

    suspend fun getGenre(): QuerySnapshot {
        return genreFirebase.get().await()
    }
}