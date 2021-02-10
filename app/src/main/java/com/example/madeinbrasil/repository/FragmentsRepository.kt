package com.example.madeinbrasil.repository

import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.utils.Constants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FragmentsRepository {
    private val auth by lazy {
        Firebase.auth
    }
    private val userFirebase by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_USERS).document(auth.currentUser?.uid ?: "")
    }

    suspend fun updateUser(user: User) {
        userFirebase.set(user, SetOptions.merge()).await()
    }

    fun signOut() {
        auth.signOut()
    }
}