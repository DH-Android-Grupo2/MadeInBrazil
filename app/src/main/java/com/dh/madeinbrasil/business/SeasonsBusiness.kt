package com.dh.madeinbrasil.business

import android.content.Context
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.model.seasons.Seasons
import com.dh.madeinbrasil.repository.SeasonsRepository

class SeasonsBusiness(val context: Context) {

    private val repository by lazy {
        SeasonsRepository(context)
    }

    suspend fun getSeasons(serieId: Int?, seasonId: Int?): ResponseAPI {
        val response = repository.getSeasonRepository(serieId, seasonId)
        return if(response is ResponseAPI.Success) {
            val season = response.data as Seasons
            season.poster_path = season.poster_path?.getFullImagePath()
            season.episodes?.forEach {ep ->
                ep.still_path = ep.still_path?.getFullImagePath()
                if(ep.overview == "") {
                    ep.overview = "Sinopse não encontrada"
                }
                if(ep.name == "") {
                    ep.name = "Título não encontrado"
                }
            }
            if(season.overview == "") {
                season.overview = "Sinopse não encontrada"
            }
            ResponseAPI.Success(season)
        }else {
            response
        }
    }
}