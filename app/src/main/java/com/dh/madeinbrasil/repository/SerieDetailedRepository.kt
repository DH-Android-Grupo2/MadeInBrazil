package com.dh.madeinbrasil.repository

import android.content.Context
import com.dh.madeinbrasil.api.APIService
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.database.MadeInBrazilDatabase
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.database.entities.cast.CastFirebase
import com.dh.madeinbrasil.database.entities.genre.GenreFirebase
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.database.entities.season.SeasonFirebase
import com.dh.madeinbrasil.model.search.ResultSearch
import com.dh.madeinbrasil.utils.Constants.Firebase.DATABASE_CAST
import com.dh.madeinbrasil.utils.Constants.Firebase.DATABASE_GENRE
import com.dh.madeinbrasil.utils.Constants.Firebase.DATABASE_MIDIA
import com.dh.madeinbrasil.utils.Constants.Firebase.DATABASE_SEASON
import com.dh.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SerieDetailedRepository(val context: Context) {
    private val searchDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).FilmsFragmentDao()
    }
    private val auth by lazy {
        Firebase.auth
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
        Firebase.firestore.collection(DATABASE_SEASON)
    }
    private val userFirebase by lazy {
        Firebase.firestore.collection(DATABASE_USERS).document(auth.currentUser?.uid ?: "")
    }



    suspend fun getSerieRepository(serieId: Int): ResponseAPI{
        return try {
            val response = APIService.tmdbApiMovieDetailed.serieDetails(serieId)

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

    suspend fun setGenreFireBase(id: Int, infos: GenreFirebase) {
        genreFirebase.document("$id").set(infos, SetOptions.merge()).await()
    }

    suspend fun setMidiaFireBase(id: Int, infos: MidiaFirebase) {
        midiaFirebase.document("$id").set(infos, SetOptions.merge()).await()
    }

    suspend fun getMidiaFireBase(id: Int): DocumentSnapshot? {
        return midiaFirebase.document("$id").get().await()
    }

    suspend fun getCast(id: Int): DocumentSnapshot? {
        return castFirebase.document("$id").get().await()
    }

    suspend fun getSeason(id: Int): DocumentSnapshot? {
        return seasonFirebase.document("$id").get().await()
    }

    suspend fun setCastFireBase(id: Int, infos: CastFirebase) {
        castFirebase.document("$id").set(infos, SetOptions.merge()).await()
    }

    suspend fun setSeasonFireBase(id: Int, infos: SeasonFirebase) {
        seasonFirebase.document("$id").set(infos, SetOptions.merge()).await()
    }

    suspend fun getSearchDB(): List<ResultSearch> {
        return searchDB.getSearchResult()
    }

}