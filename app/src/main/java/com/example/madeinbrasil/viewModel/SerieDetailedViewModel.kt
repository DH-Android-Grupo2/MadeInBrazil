package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.SerieDetailedBusiness
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.serieDetailed.Genre
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import kotlinx.coroutines.launch

class SerieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val serieDetailedSucess: MutableLiveData<SerieDetailed> = MutableLiveData()
    val serieDetailedError: MutableLiveData<List<MidiaEntity>> = MutableLiveData()

    private val midiaDB by lazy {
        MadeInBrazilDatabase.getDatabase(application).midiaDao()
    }

    private val businessDetailed by lazy {
        SerieDetailedBusiness(application)
    }

    fun getSerieDetailed(serieId: Int?) {
        viewModelScope.launch {
            when(val response = serieId?.let { businessDetailed.getSerieDetails(it)}) {
                is ResponseAPI.Success -> {
                    serieDetailedSucess.postValue(response.data as SerieDetailed)
                }
                is ResponseAPI.Error -> {
                    serieDetailedError.postValue(midiaDB.getMidia())
                }
            }
        }
    }

    fun insertFavorite(fav: Favorites) {
        viewModelScope.launch {
            businessDetailed.insertFavorite(fav)
        }
    }

    fun insertWatched(watched: Watched) {
        viewModelScope.launch {
            businessDetailed.insertWatched(watched)
        }
    }

    fun deleteByIdFavorites(id: Int) {
        viewModelScope.launch {
            businessDetailed.deleteByIdFavorites(id)
        }
    }

    fun deleteByIdWatched(id: Int) {
        viewModelScope.launch {
            businessDetailed.deleteByIdWatched(id)
        }
    }

    fun insertGenre(genre: GenreEntity) {
        viewModelScope.launch {
            businessDetailed.insertGenre(genre)
        }
    }

    fun insertSeason(season: SeasonEntity) {
        viewModelScope.launch {
            businessDetailed.insertSeason(season)
        }
    }

    fun insertRecommendation(recommendation: RecommendationMidiaCrossRef) {
        viewModelScope.launch {
            businessDetailed.insertRecommendation(recommendation)
        }
    }

    fun insertSimilar(similar: SimilarMidiaCrossRef) {
        viewModelScope.launch {
            businessDetailed.insertSimilar(similar)
        }
    }
}