package com.dh.madeinbrasil.repository

import com.dh.madeinbrasil.api.APIService
import com.dh.madeinbrasil.api.ResponseAPI
import java.lang.Exception

class FilmsRepository {

    suspend fun searchMovies(
        pageNumber: Int,
        busca: String? = "Ami"
    ): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.searchMovie(pageNumber,busca?:"Ami")

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