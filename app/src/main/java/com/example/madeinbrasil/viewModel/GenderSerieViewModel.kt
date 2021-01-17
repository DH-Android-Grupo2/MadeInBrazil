package com.example.madeinbrasil.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.GenderMovieBusiness
import com.example.madeinbrasil.business.GenderSerieBusiness
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.model.gender.GenderMovie
import com.example.madeinbrasil.model.gender.GenderSerie
import kotlinx.coroutines.launch

class GenderSerieViewModel(application: Application): AndroidViewModel(application) {
    val onResultGenresSeries: MutableLiveData<GenderSerie> = MutableLiveData()
    val onResultFailureSeries: MutableLiveData<String> = MutableLiveData()

    private val genderDB by lazy {
        MadeInBrazilDatabase.getDatabase(application).genreDao()
    }
    private val business by lazy {
        GenderSerieBusiness(application)
    }

    fun getGenres() {
        viewModelScope.launch {
            when(val response = business.getGenres()) {
                is ResponseAPI.Success -> {
                    onResultGenresSeries.postValue(
                            response.data as GenderSerie
                    )
                }
                is ResponseAPI.Error -> {
                    onResultFailureSeries.postValue(response.message)
                }
            }
        }
    }

    fun insertGenre(genre: GenreEntity) {
        viewModelScope.launch {
            business.insertGenre(genre)
        }
    }
}