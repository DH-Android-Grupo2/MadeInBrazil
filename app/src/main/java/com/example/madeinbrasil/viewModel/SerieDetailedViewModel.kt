package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.SerieDetailedBusiness
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.database.entities.season.SeasonFirebase
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.serieDetailed.Genre
import com.example.madeinbrasil.model.serieDetailed.Season
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.view.activity.MenuActivity
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class SerieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val serieDetailedSucess: MutableLiveData<SerieDetailed> = MutableLiveData()
//    val serieDetailedError: MutableLiveData<List<MidiaEntity>> = MutableLiveData()
val serieDetailedError: MutableLiveData<List<MidiaFirebase>> = MutableLiveData()

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
//                    serieDetailedError.postValue(midiaDB.getMidia())
                    serieDetailedError.postValue(MenuActivity.MIDIA)
                }
            }
        }
    }

    fun setMidiaFireBase(id: Int, infos: MidiaFirebase) {
        viewModelScope.launch {
            businessDetailed.setMidiaFireBase(id, infos)
        }
    }

    fun setGenreFireBase(id: Int, infos: GenreFirebase) {
        viewModelScope.launch {
            businessDetailed.setGenreFireBase(id, infos)
        }
    }

    fun setCastFireBase(id: Int, infos: CastFirebase) {
        viewModelScope.launch {
            businessDetailed.setCastFireBase(id, infos)
        }
    }

    fun setSeasonFireBase(id: Int, infos: SeasonFirebase) {
        viewModelScope.launch {
            businessDetailed.setSeasonFireBase(id, infos)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            businessDetailed.updateUser(user)
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