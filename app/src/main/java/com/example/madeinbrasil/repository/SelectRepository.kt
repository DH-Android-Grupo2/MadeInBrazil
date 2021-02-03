package com.example.madeinbrasil.repository

import com.example.madeinbrasil.utils.Constants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SelectRepository {
    private val db by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_USERS).document(firebaseAuth.currentUser?.uid ?: "")
    }

    private val firebaseAuth by lazy {
        Firebase.auth
    }

    suspend fun setUserGenres(genres: HashMap<String, String?>) {
        db.set(genres ?: "", SetOptions.merge()).await()
    }
}