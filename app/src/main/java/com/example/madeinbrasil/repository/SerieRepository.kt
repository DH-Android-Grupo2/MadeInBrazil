package com.example.madeinbrasil.repository

import android.util.Log
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import java.lang.Exception

class SerieRepository {
    suspend fun searchSerie(
        pageNumber: Int,
        search: String? = "test"
    ): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.searchSerie(pageNumber,search?:"test")
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