package com.dh.madeinbrasil.repository

import android.content.Context
import com.dh.madeinbrasil.api.APIService
import com.dh.madeinbrasil.api.ResponseAPI

class SeasonsRepository(context: Context) {
    suspend fun getSeasonRepository(serieId: Int?, seasonId: Int?): ResponseAPI {
        return try {
            val response = APIService.tmdbApi.seasonsEpisodes(serieId?:0, seasonId?:0)

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