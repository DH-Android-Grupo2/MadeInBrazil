package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched

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