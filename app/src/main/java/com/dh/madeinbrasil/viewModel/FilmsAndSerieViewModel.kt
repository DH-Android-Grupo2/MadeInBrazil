package com.dh.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.madeinbrasil.database.entities.cast.CastFirebase
import com.dh.madeinbrasil.database.entities.genre.GenreFirebase
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.database.entities.season.SeasonFirebase
import com.dh.madeinbrasil.repository.FilmsandSerieRepository
import kotlinx.coroutines.launch

class FilmsAndSerieViewModel: ViewModel() {
    private val repository by lazy {
        FilmsandSerieRepository()
    }
    var midia: MutableLiveData<MidiaFirebase> = MutableLiveData()
    var cast: MutableLiveData<MutableList<CastFirebase>> = MutableLiveData()
    var season: MutableLiveData<MutableList<SeasonFirebase>> = MutableLiveData()
    var genre: MutableLiveData<MutableList<GenreFirebase>> = MutableLiveData()

    fun getMidia(id: Int) {
        viewModelScope.launch {
            midia.postValue(repository.getMidia(id).toObject(MidiaFirebase::class.java))
        }
    }
    fun getCast() {
        viewModelScope.launch {
            cast.postValue(repository.getCast().toObjects(CastFirebase::class.java))
        }
    }
    fun getSeason() {
        viewModelScope.launch {
            season.postValue(repository.getSeason().toObjects(SeasonFirebase::class.java))
        }
    }
    fun getGenre() {
        viewModelScope.launch {
            genre.postValue(repository.getGenre().toObjects(GenreFirebase::class.java))
        }
    }
}