package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import com.example.madeinbrasil.repository.MovieCreditsRepository

class MovieCreditsBusiness{

    private val repository:MovieCreditsRepository by lazy {
        MovieCreditsRepository()
    }

    suspend fun getMovieCredits(query: Int?): ResponseAPI {
        val response = repository.searchMovieCredits(query)
        return if (response is ResponseAPI.Success) {
            val credits = response.data as MovieCredits
            credits.cast.forEach {
                it.profilePath = it.profilePath?.getFullImagePath()
            }
            ResponseAPI.Success(credits)
        } else {
            response
        }
    }
}