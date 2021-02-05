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
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.result.Genre
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_CAST
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_GENRE
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_MIDIA
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class MovieDetailedRepository(context: Context){
    private val watchedDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).watchedDao()
    }
    private val favoriteDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).favoriteDao()
    }
    private val genreDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).genreDao()
    }
    private val recommendationDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).recommendationDao()
    }
    private val similarDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).similarDao()
    }
    private val midiaFirebase by lazy {
        Firebase.firestore.collection(DATABASE_MIDIA)
    }
    private val auth by lazy {
        Firebase.auth
    }
    private val genreFirebase by lazy {
        Firebase.firestore.collection(DATABASE_GENRE)
    }
    private val castFirebase by lazy {
        Firebase.firestore.collection(DATABASE_CAST)
    }
    private val favFirebase by lazy {
        Firebase.firestore.collection(Constants.Firebase.DATABASE_USERS).document(auth.currentUser?.uid ?: "")
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
        favFirebase.set(user, SetOptions.merge()).await()
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

    suspend fun insertFavorite(fav: Favorites) {
        favoriteDao.insertFavorite(fav)
    }

    suspend fun deleteByIdFavorites(id: Int) {
        favoriteDao.deleteByIdFavorites(id)
    }

    suspend fun insertWatched(watched: Watched) {
        watchedDao.insertWatched(watched)
    }

    suspend fun deleteByIdWatched(id: Int) {
        watchedDao.deleteByIdWatched(id)
    }

    suspend fun insertGenre(genre: GenreEntity) {
        genreDB.insertGenre(genre)
    }

    suspend fun insertRecommendation(recommendation: RecommendationMidiaCrossRef) {
        recommendationDB.insertRecommendation(recommendation)
    }

    suspend fun insertSimilar(similar: SimilarMidiaCrossRef) {
        similarDB.insertSimilar(similar)
    }
}