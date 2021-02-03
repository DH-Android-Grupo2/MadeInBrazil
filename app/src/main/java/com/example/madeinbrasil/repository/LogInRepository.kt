package com.example.madeinbrasil.repository

import com.example.madeinbrasil.utils.Constants
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LogInRepository {
    private val firebaseAuth by lazy {
        Firebase.auth
    }
    private val db by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_USERS)
            .document(firebaseAuth.currentUser?.uid ?: "")
    }

    suspend fun signInAuthentication(email: String, password: String): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }

    suspend fun logInWithGoogle(idToken: String?, user: HashMap<String, String?>?): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
        if (user != null) {
            db.set(user, SetOptions.merge()).await()
        }
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }

    suspend fun logInWithFacebook(idToken: AccessToken, user: HashMap<String, String?>?): FirebaseUser? {
        val credential = FacebookAuthProvider.getCredential(idToken.token)
        firebaseAuth.signInWithCredential(credential).await()
        if (user != null) {
            db.set(user, SetOptions.merge()).await()
        }
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }
}