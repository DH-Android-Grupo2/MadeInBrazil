package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.database.entities.season.SeasonFirebase
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.serieDetailed.Genre
import com.example.madeinbrasil.model.serieDetailed.Season
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_CAST
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_GENRE
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_MIDIA
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_SEASON
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.example.madeinbrasil.utils.Constants.Firebase.FIELD_FAVORITES
import com.example.madeinbrasil.utils.Constants.Firebase.FIELD_WATCHED
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SerieDetailedRepository(val context: Context) {
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
}