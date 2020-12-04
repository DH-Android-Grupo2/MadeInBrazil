package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.model.trailer.Trailer
import com.example.madeinbrasil.repository.TrailerSeriesRepository

class TrailerSeriesBusiness {
    private val repository by lazy {
        TrailerSeriesRepository()
    }

    suspend fun getTrailersSeries(serieId: Int?): ResponseAPI {
        val response = repository.getTrailerSerie(serieId)
        return if(response is ResponseAPI.Success) {
            val data = response.data as Trailer
            ResponseAPI.Success(data)
        }else {
            response
        }
    }
}