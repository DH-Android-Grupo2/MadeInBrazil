package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.repository.SerieDetailedRepository

class SerieDetailedBusiness {
    private val repository: SerieDetailedRepository by lazy {
        SerieDetailedRepository()
    }

    suspend fun getSerieDetails(serieId: Int): ResponseAPI {
        val response = repository.getSerieRepository(serieId)
        return if(response is ResponseAPI.Success) {
            val serie = response.data as SerieDetailed
            serie.credits?.cast?.forEach {
                it.profile_path = it.profile_path?.getFullImagePath()
            }
            serie.watch_providers?.results?.BR?.flatrate?.forEach {
                it.logo_path = it.logo_path?.getFullImagePath()
            }
            serie.poster_path = serie.poster_path?.getFullImagePath()
            serie.backdrop_path = serie.backdrop_path?.getFullImagePath()
            serie.seasons?.forEach {
                it.poster_path = it.poster_path?.getFullImagePath()
                if(it.overview == "") {
                    it.overview = "Sinopse não encontrada"
                }
                if(it.name == "") {
                    it.name = "Título não encontrado"
                }
            }
            ResponseAPI.Success(serie)
        }else {
            response
        }
    }
}