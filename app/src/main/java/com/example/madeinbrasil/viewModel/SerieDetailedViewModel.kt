package com.example.madeinbrasil.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.SerieDetailedBusiness
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.FavoritesSerieDetailed
import com.example.madeinbrasil.database.entities.favorites.SerieDetailedWithGenres
import com.example.madeinbrasil.database.entities.watched.WatchedSerieDetailed
import com.example.madeinbrasil.model.serieDetailed.Genre
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SerieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val serieDetailedSucess: MutableLiveData<SerieDetailed> = MutableLiveData()
//    val serieDetailedError: MutableLiveData<String> = MutableLiveData()
    val serieDetailedError: MutableLiveData<List<FavoritesSerieDetailed>> = MutableLiveData()
    val getFavorite: MutableLiveData<List<FavoritesSerieDetailed>> = MutableLiveData()
    val getWatched: MutableLiveData<List<WatchedSerieDetailed>> = MutableLiveData()

    val favoriteDB = MadeInBrazilDatabase.getDatabase(application).favoriteDao()
    val watchedDB = MadeInBrazilDatabase.getDatabase(application).watchedDao()

    private val businessDetailed by lazy {
        SerieDetailedBusiness(application)
    }

    fun getSerieDetailed(serieId: Int?) {
        viewModelScope.launch {
            when(val response = serieId?.let { businessDetailed.getSerieDetails(it)}) {
                is ResponseAPI.Success -> {
                    serieDetailedSucess.postValue(response.data as SerieDetailed)
                    getFavorite.postValue(favoriteDB.getSerieFavorites())
                    getWatched.postValue(watchedDB.getSerieWatched())
                }
                is ResponseAPI.Error -> {
//                    serieDetailedError.postValue(response.message)
                    serieDetailedError.postValue(favoriteDB.getSerieFavorites())
                }
            }
        }
    }

    fun insertSerieFavorite(serie: FavoritesSerieDetailed) {
        viewModelScope.launch {
            businessDetailed.insertSerieFavorite(serie)
        }
    }

    fun insertGenreFavorite(genre: List<Genre>) {
        viewModelScope.launch {
            businessDetailed.insertGenreFavorite(genre)
        }
    }

    fun deleteSerieFavorite(serie: FavoritesSerieDetailed) {
        viewModelScope.launch {
            businessDetailed.deleteSerieFavorite(serie)
        }
    }

    fun insertSerieWatched(serie: WatchedSerieDetailed) {
        viewModelScope.launch {
            businessDetailed.insertSerieWatched(serie)
        }
    }

    fun deleteSerieWatched(serie: WatchedSerieDetailed) {
        viewModelScope.launch {
            businessDetailed.deleteSerieWatched(serie)
        }
    }

}