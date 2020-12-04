package com.example.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.SerieCreditsBusiness
import com.example.madeinbrasil.model.serieCredits.SerieCredits
import kotlinx.coroutines.launch

class SerieCreditsViewModel: ViewModel() {

    val creditsSucess: MutableLiveData<SerieCredits> = MutableLiveData()
    val creditsError: MutableLiveData<String> = MutableLiveData()

    private val business by lazy {
        SerieCreditsBusiness()
    }

    fun getCreditsSerie(query: Int?) {
        viewModelScope.launch {
            when(val response = business.getSerieCredits(query)) {
                is ResponseAPI.Success -> {
                    creditsSucess?.postValue(
                            response.data as SerieCredits
                    )
                }
                is ResponseAPI.Error -> {
                    creditsError?.postValue(response.message)
                }
            }
        }
    }
}