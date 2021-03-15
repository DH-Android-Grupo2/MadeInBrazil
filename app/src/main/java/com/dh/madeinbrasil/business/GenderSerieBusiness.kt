package com.dh.madeinbrasil.business

import android.content.Context
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.model.gender.GenderSerie
import com.dh.madeinbrasil.repository.GenderSerieRepository

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