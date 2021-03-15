package com.dh.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.business.MovieCreditsBusiness
import com.dh.madeinbrasil.model.movieCredits.MovieCredits
import kotlinx.coroutines.launch

class MovieCreditsViewModel() : ViewModel() {

    val onResultCredits: MutableLiveData<MovieCredits> = MutableLiveData()
    val onResultFailure: MutableLiveData<String>  = MutableLiveData()

    private val business by lazy {
        MovieCreditsBusiness()
    }

    fun getCredits(query: Int?) {
        viewModelScope.launch {
            when(val response = query?.let { business.getMovieCredits(it) }) {
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

