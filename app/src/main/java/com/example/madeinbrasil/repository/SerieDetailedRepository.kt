package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.serieDetailed.Genre
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_FAVORITES
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_USERS
import com.example.madeinbrasil.utils.Constants.Firebase.DATABASE_WATCHED
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class SerieDetailedRepository(val context: Context) {
    private val watchedDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).watchedDao()
    }
    private val favoriteDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).favoriteDao()
    }
    private val genderDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).genreDao()
    }
    private val seasonDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).seasonDao()
    }
    private val recommendationDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).recommendationDao()
    }
    private val similarDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).similarDao()
    }
    private val auth by lazy {
        Firebase.auth
    }
    private val favoritesFirebase by lazy {
        Firebase.firestore.collection(DATABASE_USERS).document(auth.currentUser?.uid ?: "").collection(DATABASE_FAVORITES)
    }
    private val watchedFirebase by lazy {
        Firebase.firestore.collection(DATABASE_USERS).document(auth.currentUser?.uid ?: "").collection(DATABASE_WATCHED)
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

    suspend fun setFavoriteFireBase(id: Int, infos: SerieDetailed) {
        favoritesFirebase.document("$id").set(infos, SetOptions.merge()).await()
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
        genderDao.insertGenre(genre)
    }

    suspend fun insertSeason(season: SeasonEntity) {
        seasonDB.insertSeason(season)
    }

    suspend fun insertRecommendation(recommendation: RecommendationMidiaCrossRef) {
        recommendationDB.insertRecommendation(recommendation)
    }

    suspend fun insertSimilar(similar: SimilarMidiaCrossRef) {
        similarDB.insertSimilar(similar)
    }


}