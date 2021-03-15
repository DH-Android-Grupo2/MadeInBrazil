package com.dh.madeinbrasil.business

import android.content.Context
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.model.gender.GenderMovie
import com.dh.madeinbrasil.repository.GenderMovieRepository

class GenderMovieBusiness(val context: Context) {
    private val repository by lazy {
        GenderMovieRepository(context)
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