package com.dh.madeinbrasil.business

import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.model.movieCredits.MovieCredits
import com.dh.madeinbrasil.repository.MovieCreditsRepository

class MovieCreditsBusiness{

    private val repository:MovieCreditsRepository by lazy {
        MovieCreditsRepository()
    }

    suspend fun getMovieCredits(query: Int): ResponseAPI {
        val response = repository.searchMovieCredits(query)
        return if (response is ResponseAPI.Success) {
            val credits = response.data as MovieCredits
            credits.cast?.forEach {
                it.profilePath = it.profilePath?.getFullImagePath()
            }
            ResponseAPI.Success(credits)
        } else {
            response
        }
    }
}