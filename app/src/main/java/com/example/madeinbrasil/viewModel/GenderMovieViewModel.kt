package com.example.madeinbrasil.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.GenderMovieBusiness
import com.example.madeinbrasil.model.gender.GenderMovie
import kotlinx.coroutines.launch

class GenderMovieViewModel(application: Application) : AndroidViewModel(application) {
    val onResultGenres: MutableLiveData<GenderMovie> = MutableLiveData()
    val onResultFailure: MutableLiveData<String> = MutableLiveData()

    private val business by lazy {
        GenderMovieBusiness(application)
    }

    fun getGenres() {
        viewModelScope.launch {
            when (val response = business.getGenres()) {
                is ResponseAPI.Success -> {
                    onResultGenres.postValue(
                        response.data as GenderMovie
                    )
                    Log.i("Generos", "${response.data}")
                }
                is ResponseAPI.Error -> {
                    onResultFailure.postValue(response.message)
                }
            }
        }
    }
}