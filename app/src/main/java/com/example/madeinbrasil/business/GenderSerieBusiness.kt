package com.example.madeinbrasil.business

import android.content.Context
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.model.gender.GenderSerie
import com.example.madeinbrasil.repository.GenderSerieRepository

class GenderSerieBusiness(val context: Context) {

    private val repository by lazy {
        GenderSerieRepository(context)
    }

    suspend fun getGenres(): ResponseAPI {
        val response = repository.getGenderSeries()
        return if (response is ResponseAPI.Success) {
            val genres = response.data as GenderSerie
            genres.genres?.forEach {
                when(it.id) {
                    10766 -> {it.name = "Novelas"}
                    10765 -> {it.name = "Ficção"}
                    10767 -> {it.name = "TalkShow"}
                    10768 -> {it.name = "Política"}
                    10759 -> {it.name = "Ação e Aventura"}
                }

            }
            ResponseAPI.Success(genres)
        } else {
            response
        }
    }
}