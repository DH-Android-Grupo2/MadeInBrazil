package com.dh.madeinbrasil.repository

import com.dh.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SelectRepository {
    private val db by lazy {
        Firebase.firestore.collection(DATABASE_USERS).document(firebaseAuth.currentUser?.uid ?: "")
    }

    private val firebaseAuth by lazy {
        Firebase.auth
    }

    suspend fun setUserGenres(genres: MutableList<String>) {
        db.update("genresSelected", genres).await()
    }
}