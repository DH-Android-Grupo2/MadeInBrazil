package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.MovieDetailedBusiness
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.FavoritesMovieDetailed
import com.example.madeinbrasil.database.entities.favorites.FavoritesSerieDetailed
import com.example.madeinbrasil.database.entities.watched.WatchedMovieDetailed
import com.example.madeinbrasil.model.result.MovieDetailed
import kotlinx.coroutines.launch

class MovieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val movieSucess: MutableLiveData<MovieDetailed> = MutableLiveData()
//    val movieError: MutableLiveData<String> = MutableLiveData()
    val movieError: MutableLiveData<List<FavoritesMovieDetailed>> = MutableLiveData()
    val getFavorite: MutableLiveData<List<FavoritesMovieDetailed>> = MutableLiveData()
    val getWatched: MutableLiveData<List<WatchedMovieDetailed>> = MutableLiveData()

    private val favoriteDB = MadeInBrazilDatabase.getDatabase(application).favoriteDao()
    private val watchedDB = MadeInBrazilDatabase.getDatabase(application).watchedDao()

    private val detailed by lazy {
        MovieDetailedBusiness(application)
    }

    fun getMovie(movieId: Int?) {
        viewModelScope.launch {
            when(val response = movieId?.let { detailed.getMovie(it) }) {
                is ResponseAPI.Success -> {
                    movieSucess.postValue(response.data as MovieDetailed)
                    getFavorite.postValue(favoriteDB.getMovieFavorites())
                    getWatched.postValue(watchedDB.getMovieWatched())
                }
                is ResponseAPI.Error -> {
//                    movieError.postValue(response.message)
                    movieError.postValue(favoriteDB.getMovieFavorites())
                }
            }
        }
    }

    fun insertMovieFavorite(movie: FavoritesMovieDetailed) {
        viewModelScope.launch {
            detailed.insertMovieFavorite(movie)
        }
    }

    fun deleteMovieFavorite(movie: FavoritesMovieDetailed) {
        viewModelScope.launch {
            detailed.deleteMovieFavorite(movie)
        }
    }

    fun insertMovieWatched(movie: WatchedMovieDetailed) {
        viewModelScope.launch {
            detailed.insertMovieWatched(movie)
        }
    }

    fun deleteMoviewatched(movie: WatchedMovieDetailed) {
        viewModelScope.launch {
            detailed.deleteMoviewatched(movie)
        }
    }
}