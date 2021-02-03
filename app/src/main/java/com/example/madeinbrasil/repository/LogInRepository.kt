package com.example.madeinbrasil.repository

import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LogInRepository {
    private val firebaseAuth by lazy {
        Firebase.auth
    }


    suspend fun signInAuthentication(email: String, password: String): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }

    suspend fun logInWithGoogle(idToken: String?): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }

    suspend fun logInWithFacebook(idToken: AccessToken): FirebaseUser? {
        val credential = FacebookAuthProvider.getCredential(idToken.token)
        firebaseAuth.signInWithCredential(credential).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }
}