package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.SerieDetailedBusiness
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.database.entities.season.SeasonFirebase
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import com.example.madeinbrasil.view.activity.MenuActivity
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class SerieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val serieDetailedSucess: MutableLiveData<SerieDetailed> = MutableLiveData()
    val serieDetailedError: MutableLiveData<List<MidiaFirebase>> = MutableLiveData()
    val midia: MutableLiveData<MutableList<MidiaFirebase?>> = MutableLiveData()
    val cast: MutableLiveData<MutableList<DocumentSnapshot>?> = MutableLiveData()
    val season: MutableLiveData<MutableList<DocumentSnapshot>?> = MutableLiveData()
    var listMidia = mutableListOf<MidiaFirebase?>()
    var listCast = mutableListOf<DocumentSnapshot>()
    var listSeason = mutableListOf<DocumentSnapshot>()

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

    fun getMidiaFireBase(id: Int) {
        viewModelScope.launch {
            businessDetailed.getMidiaFireBase(id)?.let {
                listMidia.add(it.toObject(MidiaFirebase::class.java))
                midia.postValue(listMidia)
            }?: run {
                midia.postValue(null)
            }
        }
    }

    fun getCast(id: Int) {
        viewModelScope.launch {
//            for (x in 0 until id) {
//                businessDetailed.getCast(id[x])?.let {
//                    listCast.add(it)
//                    if(listCast.size == id.size)
//                }?: run {
//                    cast.postValue(null)
//                }
//            }
//            cast.postValue(listCast)
            businessDetailed.getCast(id)?.let {
                listCast.add(it)
                cast.postValue(listCast)
            }?: run {
                cast.postValue(null)
            }
        }
    }

    fun getSeason(id: Int) {
        viewModelScope.launch {
            businessDetailed.getSeason(id)?.let {
                listSeason.add(it)
                season.postValue(listSeason)
            }?: run {
                season.postValue(null)
            }
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
}