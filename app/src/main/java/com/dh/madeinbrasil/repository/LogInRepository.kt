package com.dh.madeinbrasil.repository

import android.content.Context
import android.widget.Toast
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.utils.Constants
import com.facebook.AccessToken
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
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

//    suspend fun signInAuthentication(email: String, password: String,context: Context): FirebaseUser? {
//
//        return try{
//            firebaseAuth
//                .signInWithEmailAndPassword(email,password)
//                .await()
//            return firebaseAuth.currentUser
//        }catch (e : FirebaseAuthInvalidCredentialsException){
//            Toast.makeText(context, "Credenciais Inválidas", Toast.LENGTH_SHORT).show()
//            return null
//        }catch (e : Exception) {
//            Toast.makeText(context, "Um Erro ocorreu", Toast.LENGTH_SHORT).show()
//            return null
//        }
//
//    }

//    suspend fun logInWithGoogle(idToken: String?, user: User?): FirebaseUser? {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        firebaseAuth.signInWithCredential(credential).await()
//        db.get().addOnSuccessListener {
//            if (!it.exists()) {
//                user?.let {
//                    db.set(user, SetOptions.merge())
//                }
//            }
//        }
//        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
//    }

//    suspend fun logInWithFacebook(idToken: AccessToken, user: User?): FirebaseUser? {
//        val credential = FacebookAuthProvider.getCredential(idToken.token)
//        firebaseAuth.signInWithCredential(credential).await()
//        db.get().addOnSuccessListener {
//            if (!it.exists()) {
//                user?.let {
//                    db.set(user, SetOptions.merge())
//                }
//            }
//        }
//        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
//    }

    suspend fun signInAuthentication(email: String, password: String,context: Context): String? {
        return try{
            firebaseAuth
                .signInWithEmailAndPassword(email,password)
                .await()
            firebaseAuth.currentUser?.uid
        }catch (e : FirebaseAuthInvalidCredentialsException){
            Toast.makeText(context, "Credenciais Inválidas", Toast.LENGTH_SHORT).show()
            null
        }catch (e : Exception) {
            Toast.makeText(context, "Um Erro ocorreu", Toast.LENGTH_SHORT).show()
            null
        }
    }

    suspend fun logInWithGoogle(idToken: String?): DocumentSnapshot? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
        return db.get().await()
    }

    suspend fun logInWithFacebook(idToken: AccessToken): DocumentSnapshot? {
        val credential = FacebookAuthProvider.getCredential(idToken.token)
        firebaseAuth.signInWithCredential(credential).await()
        return db.get().await()
    }

    suspend fun setFacebookLogin(user: User) {
        db.set(user, SetOptions.merge()).await()
    }
}