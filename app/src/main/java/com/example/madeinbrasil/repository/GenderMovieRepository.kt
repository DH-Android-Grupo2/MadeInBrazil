package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import java.lang.Exception

class GenderMovieRepository(context: Context) {
    private val genderDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).genreDao()
    }

    suspend fun getGenderMovies(): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.movieGenres()

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

    suspend fun insertGenre(genre: GenreEntity) {
        genderDB.insertGenre(genre)
    }
}