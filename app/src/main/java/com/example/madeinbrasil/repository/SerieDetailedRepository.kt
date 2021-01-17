package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.serieDetailed.Genre

class SerieDetailedRepository(val context: Context) {
    private val watchedDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).watchedDao()
    }
    private val favoriteMidiaDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).favoriteMidiaDao()
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

    suspend fun insertMidia(midia: MidiaEntity) {
        favoriteMidiaDao.insertMidia(midia)

    }

    suspend fun insertFavorite(fav: Favorites) {
        favoriteMidiaDao.insertFavorite(fav)
    }

    suspend fun deleteByIdFavorites(id: Int) {
        favoriteMidiaDao.deleteByIdFavorites(id)
    }

    suspend fun insertWatched(watched: Watched) {
        watchedDao.insertWatched(watched)
    }

    suspend fun deleteByIdWatched(id: Int) {
        watchedDao.deleteByIdWatched(id)
    }
}