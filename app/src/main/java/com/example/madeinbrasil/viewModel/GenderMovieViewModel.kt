package com.example.madeinbrasil.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.GenderMovieBusiness
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.model.gender.GenderMovie
import com.example.madeinbrasil.model.nowPlaying.NowPlayingDataSourceFactory
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.model.upcoming.UpcomingDataSourceFactory
import com.example.madeinbrasil.utils.Constants
import kotlinx.coroutines.launch

class GenderMovieViewModel(application: Application) : AndroidViewModel(application) {

    val onResultGenres: MutableLiveData<GenderMovie> = MutableLiveData()
    val onResultFailure: MutableLiveData<String> = MutableLiveData()

    private val business by lazy {
        GenderMovieBusiness(application)
    }

    fun getGenres() {
        viewModelScope.launch {
            when(val response = business.getGenres()) {
                is ResponseAPI.Success -> {
                    onResultGenres.postValue(
                        response.data as GenderMovie
                    )
                    Log.i("Generos","${response.data}")
                }
                is ResponseAPI.Error -> {
                    onResultFailure.postValue(response.message)
                }
            }
        }
    }
    fun insertGenre(genre: GenreEntity) {
        viewModelScope.launch {
            business.insertGenre(genre)
        }
    }

}