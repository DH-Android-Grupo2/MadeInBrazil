package com.dh.madeinbrasil.repository

import com.dh.madeinbrasil.api.APIService
import com.dh.madeinbrasil.api.ResponseAPI
import java.lang.Exception

class MovieCreditsRepository {

    suspend fun searchMovieCredits(
        busca: Int? = 0
    ): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.movieCredits(busca?:0)

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