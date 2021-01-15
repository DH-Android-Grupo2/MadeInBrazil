package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.FavoritesMovieDetailed
import com.example.madeinbrasil.database.entities.watched.WatchedMovieDetailed

class MovieDetailedRepository(context: Context){
    private val favoriteDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).favoriteDao()
    }
    private val watchedDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).watchedDao()
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

    suspend fun insertMovieFavorite(movie: FavoritesMovieDetailed) {
        favoriteDao.insertMovie(movie)
    }

    suspend fun deleteMovieFavorite(movie: FavoritesMovieDetailed) {
        favoriteDao.deleteMovie(movie)
    }

    suspend fun insertMovieWatched(movie: WatchedMovieDetailed) {
        watchedDao.insertMovieWatched(movie)
    }

    suspend fun deleteMoviewatched(movie: WatchedMovieDetailed) {
        watchedDao.deleteWatchedMovie(movie)
    }
}