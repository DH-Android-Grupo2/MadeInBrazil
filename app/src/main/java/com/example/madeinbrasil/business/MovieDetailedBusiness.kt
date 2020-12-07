package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import com.example.madeinbrasil.model.result.MovieDetailed
import com.example.madeinbrasil.model.trailer.Trailer
import com.example.madeinbrasil.repository.MovieDetailedRepository
import com.example.madeinbrasil.repository.TrailerRepository
import com.example.madeinbrasil.utils.Constants

class MovieDetailedBusiness  {

    private val repository:MovieDetailedRepository by lazy {
        MovieDetailedRepository()
    }

    suspend fun getMovie(movieId: Int): ResponseAPI {
        val response = repository.getMovie(movieId)
        return if (response is ResponseAPI.Success) {
            val movie = response.data as MovieDetailed
            movie.credits.cast.forEach {
                it.profile_path = it.profile_path?.getFullImagePath()
            }
            movie.watch_providers?.results?.BR?.flatrate?.forEach {
                it.logo_path = it.logo_path?.getFullImagePath()
            }
            ResponseAPI.Success(movie)
        } else {
            response
        }
    }
}