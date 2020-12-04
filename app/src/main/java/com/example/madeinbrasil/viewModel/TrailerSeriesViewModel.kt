package com.example.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.TrailerSeriesBusiness
import com.example.madeinbrasil.model.trailer.Trailer
import kotlinx.coroutines.launch

class TrailerSeriesViewModel: ViewModel() {

    val trailerSucessSerie: MutableLiveData<Trailer> = MutableLiveData()
    val trailerErrorSerie: MutableLiveData<String> = MutableLiveData()

    private val trailer by lazy {
        TrailerSeriesBusiness()
    }

    fun getTrailerSerie(serieId: Int?) {
        viewModelScope.launch {
            when(val response = trailer.getTrailersSeries(serieId)) {
                is ResponseAPI.Success -> {
                    trailerSucessSerie.postValue(response.data as Trailer)
                }
                is ResponseAPI.Error -> {
                    trailerErrorSerie.postValue(response.message)
                }
            }
        }
    }
}