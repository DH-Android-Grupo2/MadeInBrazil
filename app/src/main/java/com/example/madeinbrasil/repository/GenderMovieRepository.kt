package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import java.lang.Exception

class GenderMovieRepository(context: Context) {
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
}