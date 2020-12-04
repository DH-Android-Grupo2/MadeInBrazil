package com.example.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.TrailerBusiness
import com.example.madeinbrasil.model.trailer.Trailer

import kotlinx.coroutines.launch

class TrailerViewModel: ViewModel() {
    val trailerSucess: MutableLiveData<Trailer> = MutableLiveData()
    val trailerError: MutableLiveData<String> = MutableLiveData()

    private val trailer by lazy {
        TrailerBusiness()
    }

    fun getTrailer(movieId: Int?) {
        viewModelScope.launch {
            when(val response = trailer.getTrailers(movieId)) {
                is ResponseAPI.Success -> {
                    trailerSucess.postValue(response.data as Trailer)
                }
                is ResponseAPI.Error -> {
                    trailerError.postValue(response.message)
                }
            }
        }
    }
}