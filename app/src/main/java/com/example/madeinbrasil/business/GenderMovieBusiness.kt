package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.model.gender.GenderMovie
import com.example.madeinbrasil.model.upcoming.Upcoming
import com.example.madeinbrasil.repository.GenderMovieRepository
import com.example.madeinbrasil.utils.Constants

class GenderMovieBusiness {
    private val repository by lazy {
        GenderMovieRepository()
    }

    suspend fun getGenres(): ResponseAPI {
        val response = repository.getGenderMovies()
        return if (response is ResponseAPI.Success) {
            val genres = response.data as GenderMovie
            ResponseAPI.Success(genres)
        } else {
            response
        }
    }
}