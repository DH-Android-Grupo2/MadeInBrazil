package com.example.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.MovieDetailedBusiness
import com.example.madeinbrasil.model.result.MovieDetailed
import kotlinx.coroutines.launch

class MovieDetailedViewModel: ViewModel() {
    val movieSucess: MutableLiveData<MovieDetailed> = MutableLiveData()
    val movieError: MutableLiveData<String> = MutableLiveData()

    private val detailed by lazy {
        MovieDetailedBusiness()
    }

    fun getMovie(movieId: Int?) {
        viewModelScope.launch {
            when(val response = movieId?.let { detailed.getMovie(it) }) {
                is ResponseAPI.Success -> {
                    movieSucess.postValue(
                        response.data as MovieDetailed
                    )
                }
                is ResponseAPI.Error -> {
                    movieError.postValue(response.message)
                }
            }
        }
    }
}