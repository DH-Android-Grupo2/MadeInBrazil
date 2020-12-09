package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.repository.MovieDetailedRepository

class MovieDetailedBusiness  {

    private val repository:MovieDetailedRepository by lazy {
        MovieDetailedRepository()
    }

    suspend fun getMovie(movieId: Int): ResponseAPI {
        val response = repository.getMovie(movieId)
        return if (response is ResponseAPI.Success) {
            val movie = response.data as MovieDetailed
            movie?.credits?.cast?.forEach {
                it.profilePath = it.profilePath?.getFullImagePath()
            }
            ResponseAPI.Success(movie)
        } else {
            response
        }
    }
}