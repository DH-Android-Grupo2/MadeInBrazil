package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.FavoritesSerieDetailed
import com.example.madeinbrasil.database.entities.watched.WatchedMovieDetailed
import com.example.madeinbrasil.database.entities.watched.WatchedSerieDetailed
import com.example.madeinbrasil.model.serieDetailed.Genre

class SerieDetailedRepository(val context: Context) {
    private val favoriteDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).favoriteDao()
    }
    private val watchedDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).watchedDao()
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

    suspend fun insertSerieFavorite(serie: FavoritesSerieDetailed) {
        favoriteDao.insertSerie(serie)
    }

    suspend fun insertGenreFavorite(genre: List<Genre>) {
        favoriteDao.insertGenre(genre)
    }

    suspend fun getSerieDetailedWithGenre() {
        favoriteDao.getSerieDetailedWithGenre()
    }

    suspend fun deleteSerieFavorite(serie: FavoritesSerieDetailed) {
        favoriteDao.deleteSerie(serie)
    }

    suspend fun insertSerieWatched(serie: WatchedSerieDetailed) {
        watchedDao.insertSerieWatched(serie)
    }

    suspend fun deleteSerieWatched(serie: WatchedSerieDetailed) {
        watchedDao.deleteWatchedSerie(serie)
    }
}