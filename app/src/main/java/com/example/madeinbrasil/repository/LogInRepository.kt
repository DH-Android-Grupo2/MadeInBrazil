package com.example.madeinbrasil.repository

import android.util.Log
import android.widget.Toast
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.utils.Constants
import com.facebook.AccessToken
import com.google.firebase.auth.*
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

    suspend fun signInAuthentication(email: String, password: String,context: Context): FirebaseUser? {

        return try{
            firebaseAuth
                .signInWithEmailAndPassword(email,password)
                .await()
            return firebaseAuth.currentUser
        }catch (e : FirebaseAuthUserCollisionException){
            Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
            return null
        }catch (e : FirebaseAuthInvalidCredentialsException){
            Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
            return null
        }catch (e : Exception) {
            return null
        }

    }

    suspend fun logInWithGoogle(idToken: String?, user: User?): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
        db.get().addOnSuccessListener {
            if (!it.exists()) {
                user?.let {
                    db.set(user, SetOptions.merge())
                }
            }
        }
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }

    suspend fun logInWithFacebook(idToken: AccessToken, user: User?): FirebaseUser? {
        val credential = FacebookAuthProvider.getCredential(idToken.token)
        firebaseAuth.signInWithCredential(credential).await()
        db.get().addOnSuccessListener {
            if (!it.exists()) {
                user?.let {
                    db.set(user, SetOptions.merge())
                }
            }
        }
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }
}