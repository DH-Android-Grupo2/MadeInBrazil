package com.example.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.SeasonsBusiness
import com.example.madeinbrasil.model.seasons.Seasons
import kotlinx.coroutines.launch

class SeasonsViewModel: ViewModel() {

    val seasonSucess: MutableLiveData<Seasons> = MutableLiveData()
    val seasonError: MutableLiveData<String> = MutableLiveData()
    private val businessDetailed by lazy {
        SeasonsBusiness()
    }

    fun getSeasons(serieId: Int?, seasonId: Int?) {
        viewModelScope.launch {
            when(val response = serieId?.let { businessDetailed.getSeasons(it, seasonId)}) {
                is ResponseAPI.Success -> {
                    seasonSucess.postValue(response.data as Seasons)
                }
                is ResponseAPI.Error -> {
                    seasonError.postValue(response.message)
                }
            }
        }
    }
}