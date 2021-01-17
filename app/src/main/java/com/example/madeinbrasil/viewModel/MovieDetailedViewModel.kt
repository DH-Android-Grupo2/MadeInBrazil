package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.MovieDetailedBusiness
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.result.MovieDetailed
import kotlinx.coroutines.launch

class MovieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val movieSucess: MutableLiveData<MovieDetailed> = MutableLiveData()
    val movieError: MutableLiveData<List<MidiaEntity>> = MutableLiveData()

    private val midiaDB by lazy {
        MadeInBrazilDatabase.getDatabase(application).midiaDao()
    }
    private val detailed by lazy {
        MovieDetailedBusiness(application)
    }

    fun getMovie(movieId: Int?) {
        viewModelScope.launch {
            when(val response = movieId?.let { detailed.getMovie(it) }) {
                is ResponseAPI.Success -> {
                    movieSucess.postValue(response.data as MovieDetailed)
                }
                is ResponseAPI.Error -> {
                    movieError.postValue(midiaDB.getMidia())
                }
            }
        }
    }

    fun insertFavorite(fav: Favorites) {
        viewModelScope.launch {
            detailed.insertFavorite(fav)
        }
    }

    fun insertWatched(watched: Watched) {
        viewModelScope.launch {
            detailed.insertWatched(watched)
        }
    }

    fun deleteByIdFavorites(id: Int) {
        viewModelScope.launch {
            detailed.deleteByIdFavorites(id)
        }
    }

    fun deleteByIdWatched(id: Int) {
        viewModelScope.launch {
            detailed.deleteByIdWatched(id)
        }
    }
    fun insertGenre(genre: GenreEntity) {
        viewModelScope.launch {
            detailed.insertGenre(genre)
        }
    }
}