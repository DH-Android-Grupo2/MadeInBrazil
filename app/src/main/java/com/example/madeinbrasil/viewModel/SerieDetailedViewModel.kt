package com.example.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
//import com.example.madeinbrasil.business.SerieDetailedBusiness
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
import kotlinx.coroutines.launch

//class SerieDetailedViewModel: ViewModel() {
//    val serieDetailedSucess: MutableLiveData<SerieDetailed> = MutableLiveData()
//    val serieDetailedError: MutableLiveData<String> = MutableLiveData()
//    private val businessDetailed by lazy {
//        SerieDetailedBusiness()
//    }
//
//    fun getSerieDetailed(serieId: Int?) {
//        viewModelScope.launch {
//            when(val response = serieId?.let { businessDetailed.getSerieDetails(it)}) {
//                is ResponseAPI.Success -> {
//                    serieDetailedSucess.postValue(response.data as SerieDetailed)
//                }
//                is ResponseAPI.Error -> {
//                    serieDetailedError.postValue(response.message)
//                }
//            }
//        }
//    }
//}