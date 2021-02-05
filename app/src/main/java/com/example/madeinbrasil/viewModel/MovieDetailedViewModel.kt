package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.MovieDetailedBusiness
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.result.Genre
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.view.activity.MenuActivity
import kotlinx.coroutines.launch

class MovieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val movieSucess: MutableLiveData<MovieDetailed> = MutableLiveData()
//    val movieError: MutableLiveData<List<MidiaEntity>> = MutableLiveData()
    val movieError: MutableLiveData<List<MidiaFirebase>> = MutableLiveData()

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
//                    movieError.postValue(midiaDB.getMidia())
                    movieError.postValue(MenuActivity.MIDIA)
                }
            }
        }
    }

    fun setMidiaFireBase(id: Int, infos: MidiaFirebase) {
        viewModelScope.launch {
            detailed.setMidiaFireBase(id, infos)
        }
    }

    fun setGenreFireBase(id: Int, infos: GenreFirebase) {
        viewModelScope.launch {
            detailed.setGenreFireBase(id, infos)
        }
    }

    fun setCastFireBase(id: Int, infos: CastFirebase) {
        viewModelScope.launch {
            detailed.setCastFireBase(id, infos)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            detailed.updateUser(user)
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
    fun insertRecommendation(recommendation: RecommendationMidiaCrossRef) {
        viewModelScope.launch {
            detailed.insertRecommendation(recommendation)
        }
    }

    fun insertSimilar(similar: SimilarMidiaCrossRef) {
        viewModelScope.launch {
            detailed.insertSimilar(similar)
        }
    }

}