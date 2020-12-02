package com.example.madeinbrasil.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.MovieCreditsBusiness
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import kotlinx.coroutines.launch
import retrofit2.http.Query

class MovieCreditsViewModel() : ViewModel() {

    val onResultCredits: MutableLiveData<MovieCredits> = MutableLiveData()
    val onResultFailure: MutableLiveData<String>  = MutableLiveData()

    private val business by lazy {
        MovieCreditsBusiness()
    }

    fun getCredits(query: Int?) {
        viewModelScope.launch {
            when(val response = business.getMovieCredits(query)) {
                is ResponseAPI.Success -> {
                    onResultCredits?.postValue(
                        response.data as MovieCredits
                    )
                }
                is ResponseAPI.Error -> {
                    onResultFailure?.postValue(response.message)
                }
            }
        }
    }

}

