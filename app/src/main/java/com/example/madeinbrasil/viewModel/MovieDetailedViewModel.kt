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
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.view.activity.MenuActivity
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class MovieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val movieSucess: MutableLiveData<MovieDetailed> = MutableLiveData()
    val movieError: MutableLiveData<List<MidiaFirebase>> = MutableLiveData()
    val midia: MutableLiveData<MutableList<MidiaFirebase?>> = MutableLiveData()
    val cast: MutableLiveData<MutableList<DocumentSnapshot>> = MutableLiveData()
    var listMidia = mutableListOf<MidiaFirebase?>()
    var listCast = mutableListOf<DocumentSnapshot>()
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

    fun getMidiaFireBase(id: Int) {
        viewModelScope.launch {
            detailed.getMidiaFireBase(id)?.let {
                listMidia.add(it.toObject(MidiaFirebase::class.java))
                midia.postValue(listMidia)
            }?: run {
                midia.postValue(null)
            }
        }
    }

    fun getCast(id: Int) {
        viewModelScope.launch {
            detailed.getCast(id)?.let {
                listCast.add(it)
                cast.postValue(listCast)
            }?: run {
                cast.postValue(null)
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
}