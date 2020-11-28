package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.model.upcoming.Upcoming
import com.example.madeinbrasil.repository.HomeRepository
import com.example.madeinbrasil.utils.Constants.Api.BASE_URL_ORIGINAL_IMAGE

class HomeBusiness {
    private val repository by lazy {
        HomeRepository()
    }

    suspend fun getRated(pageNumber: Int): ResponseAPI {
        val response = repository.getUpcoming(pageNumber)
        return if (response is ResponseAPI.Success) {
            val upcoming = response.data as Upcoming
            upcoming.results.forEach {
                it.posterPath = "$BASE_URL_ORIGINAL_IMAGE${it.posterPath}"
            }
            ResponseAPI.Success(upcoming)
        } else {
            response
        }
    }
}