package com.example.madeinbrasil.business

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.entities.season.EpisodeEntity
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.seasons.Seasons
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.repository.SeasonsRepository
import kotlinx.coroutines.launch

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

    suspend fun insertEpisode(ep: EpisodeEntity) {
        repository.insertEpisode(ep)
    }
}