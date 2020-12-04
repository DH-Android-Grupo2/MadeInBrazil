package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.serieCredits.SerieCredits
import com.example.madeinbrasil.repository.SerieCreditsRepository

class SerieCreditsBusiness {

    private val repository: SerieCreditsRepository by lazy {
        SerieCreditsRepository()
    }

    suspend fun getSerieCredits(query: Int?): ResponseAPI {
        return when(val response = repository.searchSerieCredits(query)) {
            is ResponseAPI.Success -> {
                val credits = response.data as SerieCredits

                credits.cast.forEach { cast ->
                    cast.profilePath = cast.profilePath?.getFullImagePath()
                }
                ResponseAPI.Success(credits)
            }
            else -> {
                response
            }
        }
    }
}