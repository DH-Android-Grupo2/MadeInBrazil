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
                it.profilePath = it.profilePath?.getFullImagePath()
            }
            serie.watch_providers?.results?.BR?.flatrate?.forEach {
                it.logoPath = it.logoPath?.getFullImagePath()
            }
            serie.posterPath = serie.posterPath?.getFullImagePath()
            serie.backdropPath?.let { string ->
                serie.backdropPath = string.getFullImagePath()
            }.also {
                serie.backdropPath = serie.posterPath
            }
            if(serie.overview == "") {
                serie.overview = "Sinopse não encontrada"
            }
            serie.seasons?.forEach {
                it.posterPath = it.posterPath?.getFullImagePath()
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