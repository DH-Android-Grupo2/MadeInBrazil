package com.example.madeinbrasil.repository

import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI

class TrailerSeriesRepository {

    suspend fun getTrailerSerie(serieId: Int?): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.trailerSeries(serieId?:0)

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