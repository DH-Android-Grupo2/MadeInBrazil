package com.example.madeinbrasil.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.example.madeinbrasil.view.activity.RegisterActivity
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class RegisterRepository() {
    private val firebaseAuth by lazy {
        Firebase.auth
    }
    private val db by lazy {
        Firebase.firestore.collection(DATABASE_USERS)
            .document(firebaseAuth.currentUser?.uid ?: "")
    }

    suspend fun createNewUser(email: String, password: String, user: User,context: Context): FirebaseUser? {

        return try{
            firebaseAuth
                .createUserWithEmailAndPassword(email,password)
                .await()
            db.set(user, SetOptions.merge()).await()
            return firebaseAuth.currentUser
        }catch (e : FirebaseAuthUserCollisionException){
            Toast.makeText(context, "O Email já está em Uso", Toast.LENGTH_SHORT).show()
            return null
        }catch (e : FirebaseAuthInvalidCredentialsException){
            Toast.makeText(context, "Credenciais Inválidas", Toast.LENGTH_SHORT).show()
            return null
        }catch (e : FirebaseAuthWeakPasswordException) {
            Toast.makeText(context, "Senha está Fraca!", Toast.LENGTH_SHORT).show()
            return null
        }catch (e : Exception) {
            Toast.makeText(context, "Um Erro ocorreu", Toast.LENGTH_SHORT).show()
            return null
        }

    }
}