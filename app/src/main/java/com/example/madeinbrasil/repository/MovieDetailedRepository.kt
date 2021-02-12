package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_CAST
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_GENRE
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_MIDIA
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.tasks.await
import com.example.madeinbrasil.model.classe.CommentFirebase
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.Result

class MovieDetailedRepository(context: Context){
    private val searchDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).FilmsFragmentDao()
    }
    private val midiaFirebase by lazy {
        Firebase.firestore.collection(DATABASE_MIDIA)
    }
    private val genreFirebase by lazy {
        Firebase.firestore.collection(DATABASE_GENRE)
    }
    private val castFirebase by lazy {
        Firebase.firestore.collection(DATABASE_CAST)
    }
    private val seasonFirebase by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_SEASON)
    }
    private val auth by lazy {
        Firebase.auth
    }
    private val userFirebase by lazy {
        Firebase.firestore.collection(DATABASE_USERS).document(auth.currentUser?.uid ?: "")
    }

    private val firebaseComments by lazy {
        Firebase.firestore
    }

    suspend fun getMovie(movieId: Int): ResponseAPI {
        return try {
            val response = APIService.tmdbApiMovieDetailed.movieDetails(movieId)

            if (response.isSuccessful) {
                ResponseAPI.Success(response.body())
            } else {
                if (response.code() == 404) {
                    ResponseAPI.Error("Dado não encontrado")
                } else {
                    ResponseAPI.Error("Erro ao carregar os dados")
                }
            }
        } catch (exception: Exception) {
            ResponseAPI.Error("Erro ao carregar os dados")
        }
    }

    suspend fun updateUser(user: User) {
        userFirebase.set(user, SetOptions.merge()).await()
    }

    suspend fun getMidiaFireBase(id: Int): DocumentSnapshot? {
        return midiaFirebase.document("$id").get().await()
    }

    suspend fun getCast(id: Int): DocumentSnapshot? {
        return castFirebase.document("$id").get().await()
    }

    suspend fun setMidiaFireBase(id: Int, infos: MidiaFirebase) {
        midiaFirebase.document("$id").set(infos, SetOptions.merge()).await()
    }

    suspend fun setGenreFireBase(id: Int, infos: GenreFirebase) {
        genreFirebase.document("$id").set(infos, SetOptions.merge()).await()
    }

    suspend fun setCastFireBase(id: Int, infos: CastFirebase) {
        castFirebase.document("$id").set(infos, SetOptions.merge()).await()
    }

    suspend fun getSearchDB(): List<Result> {
        return searchDB.getResults()
    }

    suspend fun postComment(hashMap: Any,id: Int) {
        firebaseComments.collection("commentsByMedia").document(id.toString()).collection("comments").document().set(hashMap).await()
    }

    suspend fun getComment(id: Int): MutableList<CommentFirebase?> {
        val docRef = firebaseComments.collection("commentsByMedia").document(id.toString()).collection("comments")

        return try {
           val documments =  docRef.get().await()
            val commentList = mutableListOf<CommentFirebase?>()
            documments.toObjects(CommentFirebase::class.java)
            documments.forEach {
                commentList.add(it.toObject())
            }
            return commentList
        } catch (e:Exception){
            return mutableListOf()
        }

    }

}
