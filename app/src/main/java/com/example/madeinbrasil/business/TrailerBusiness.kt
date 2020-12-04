package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.model.trailer.Trailer
import com.example.madeinbrasil.repository.TrailerRepository

class TrailerBusiness {
    private val repository by lazy {
        TrailerRepository()
    }

    suspend fun getTrailers(movieId: Int?): ResponseAPI {
        val response = repository.getTrailer(movieId)
        return if(response is ResponseAPI.Success) {
            val data = response.data as Trailer
//            data.results?.forEach { result ->
//                result.key  = result.key.getYoutubePath()
//            }
            ResponseAPI.Success(data)
        }else {
            response
        }
    }
}
