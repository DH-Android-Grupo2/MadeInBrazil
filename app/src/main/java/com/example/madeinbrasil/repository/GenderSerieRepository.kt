package com.example.madeinbrasil.repository

import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import java.lang.Exception

class GenderSerieRepository() {

    suspend fun getGenderSeries(): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.serieGenders()

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