package com.dh.madeinbrasil.business

import android.content.Context
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.database.entities.cast.CastFirebase
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.database.entities.genre.GenreFirebase
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.database.entities.season.SeasonFirebase
import com.dh.madeinbrasil.model.search.ResultSearch
import com.dh.madeinbrasil.model.serieDetailed.SerieDetailed
import com.dh.madeinbrasil.repository.SerieDetailedRepository
import com.google.firebase.firestore.DocumentSnapshot

class SerieDetailedBusiness (val context: Context) {
    private val repository: SerieDetailedRepository by lazy {
        SerieDetailedRepository(context)
    }

    suspend fun getSerieDetails(serieId: Int): ResponseAPI {
        val response = repository.getSerieRepository(serieId)
        return if(response is ResponseAPI.Success) {
            val serie = response.data as SerieDetailed

            serie.credits?.cast?.forEach {
                it.profilePath = it.profilePath?.getFullImagePath()
            }
            serie.watchProviders?.results?.BR?.flatrate?.forEach {
                it.logoPath = it.logoPath?.getFullImagePath()
            }
            serie.posterPath = serie.posterPath?.getFullImagePath()

            serie.recommendations?.results = serie.recommendations?.results?.filter { it.originalLanguage.equals("pt") }
            serie.recommendations?.results?.forEach {
                it.posterPath = it.posterPath?.getFullImagePath()
            }
            serie.similar?.results = serie.similar?.results?.filter { it.originalLanguage.equals("pt") }
            serie.similar?.results?.forEach {
                it.posterPath = it.posterPath?.getFullImagePath()
            }

            serie.backdropPath?.let { string ->
                serie.backdropPath = string.getFullImagePath()
            } ?: run {
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

    suspend fun setMidiaFireBase(id: Int, infos: MidiaFirebase) {
        repository.setMidiaFireBase(id, infos)
    }

    suspend fun getMidiaFireBase(id: Int): DocumentSnapshot? {
        repository.getMidiaFireBase(id)?.let {
            return it
        }?: run {
            return null
        }
    }

    suspend fun getCast(id: Int): DocumentSnapshot? {
        repository.getCast(id)?.let {
            return it
        }?: run {
            return null
        }
    }

    suspend fun getSeason(id: Int): DocumentSnapshot? {
        repository.getSeason(id)?.let {
            return it
        }?: run {
            return null
        }
    }

    suspend fun setGenreFireBase(id: Int, infos: GenreFirebase) {
        repository.setGenreFireBase(id, infos)
    }

    suspend fun setCastFireBase(id: Int, infos: CastFirebase) {
        repository.setCastFireBase(id, infos)
    }

    suspend fun setSeasonFireBase(id: Int, infos: SeasonFirebase) {
        repository.setSeasonFireBase(id, infos)
    }

    suspend fun updateUser(user: User) {
        repository.updateUser(user)
    }

    suspend fun getSearchDB(): List<ResultSearch> {
        return repository.getSearchDB()
    }
}