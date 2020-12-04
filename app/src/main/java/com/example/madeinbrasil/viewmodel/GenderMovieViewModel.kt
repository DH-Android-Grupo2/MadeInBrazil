package com.example.madeinbrasil.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.GenderMovieBusiness
import com.example.madeinbrasil.model.gender.GenderMovie
import com.example.madeinbrasil.model.nowPlaying.NowPlayingDataSourceFactory
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.model.upcoming.UpcomingDataSourceFactory
import com.example.madeinbrasil.utils.Constants
import kotlinx.coroutines.launch

class GenderMovieViewModel : ViewModel() {

    val onResultGenres: MutableLiveData<GenderMovie> = MutableLiveData()
    val onResultFailure: MutableLiveData<String> = MutableLiveData()

    private val business by lazy {
        GenderMovieBusiness()
    }

    fun getGenres() {
        viewModelScope.launch {
            when(val response = business.getGenres()) {
                is ResponseAPI.Success -> {
                    onResultGenres.postValue(
                        response.data as GenderMovie
                    )
                }
                is ResponseAPI.Error -> {
                    onResultFailure.postValue(response.message)
                }
            }
        }
    }
}