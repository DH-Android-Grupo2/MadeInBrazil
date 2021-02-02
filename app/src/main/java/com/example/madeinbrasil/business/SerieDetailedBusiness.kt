package com.example.madeinbrasil.business

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.serieDetailed.Genre
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.repository.SerieDetailedRepository
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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

    suspend fun setFavoriteFireBase(id: Int, infos: SerieDetailed) {
        repository.setFavoriteFireBase(id, infos)
    }

    suspend fun insertFavorite(fav: Favorites) {
        repository.insertFavorite(fav)
    }

    suspend fun deleteByIdFavorites(id: Int) {
        repository.deleteByIdFavorites(id)
    }

    suspend fun insertWatched(watched: Watched) {
        repository.insertWatched(watched)
    }

    suspend fun deleteByIdWatched(id: Int) {
        repository.deleteByIdWatched(id)
    }

    suspend fun insertGenre(genre: GenreEntity) {
        repository.insertGenre(genre)
    }

    suspend fun insertSeason(season: SeasonEntity) {
        repository.insertSeason(season)
    }

    suspend fun insertRecommendation(recommendation: RecommendationMidiaCrossRef) {
        repository.insertRecommendation(recommendation)
    }

    suspend fun insertSimilar(similar: SimilarMidiaCrossRef) {
        repository.insertSimilar(similar)
    }
}