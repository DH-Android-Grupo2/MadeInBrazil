package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.model.search.movie.SearchMovie
import com.example.madeinbrasil.model.trailer.Trailer
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.repository.TrailerRepository
import com.example.madeinbrasil.utils.Constants.Api.BASE_URL_YOUTUBE_APP

class TrailerBusiness {
    private val repository by lazy {
        TrailerRepository()
    }

    suspend fun getTrailers(movieId: Int): ResponseAPI {
        return when(val response = repository.getTrailer(movieId)) {
            is ResponseAPI.Success -> {
                val data = response.data as Trailer
                data.result.forEach {result ->
                    result.key = "${BASE_URL_YOUTUBE_APP}${result.key}"
                }
                ResponseAPI.Success(data)
            }
            else -> {
                response
            }
        }
    }
}