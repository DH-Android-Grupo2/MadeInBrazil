package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.season.EpisodeEntity
import com.example.madeinbrasil.database.entities.season.SeasonEntity

class SeasonsRepository(context: Context) {
    private val seasonDB by lazy {
        MadeInBrazilDatabase.getDatabase(context).seasonDao()
    }

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

    suspend fun insertEpisode(ep: EpisodeEntity) {
        seasonDB.insertEpisode(ep)
    }
}