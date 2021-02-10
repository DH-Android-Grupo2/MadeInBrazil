package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.GenderSerieBusiness
import com.example.madeinbrasil.model.gender.GenderSerie
import kotlinx.coroutines.launch

class GenderSerieViewModel(application: Application): AndroidViewModel(application) {
    val onResultGenresSeries: MutableLiveData<GenderSerie> = MutableLiveData()
    val onResultFailureSeries: MutableLiveData<String> = MutableLiveData()
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
}