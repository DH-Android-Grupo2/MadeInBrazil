package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed
//import com.example.madeinbrasil.repository.SerieDetailedRepository

//class SerieDetailedBusiness {
//    private val repository: SerieDetailedRepository by lazy {
//        SerieDetailedRepository()
//    }
//
//    suspend fun getSerieDetails(serieId: Int): ResponseAPI {
//        val response = repository.getSerieRepository(serieId)
//        return if(response is ResponseAPI.Success) {
//            val serie = response.data as SerieDetailed
//            serie.credits?.cast?.forEach {
//                it.profile_path = it.profile_path?.getFullImagePath()
//            }
//            ResponseAPI.Success(serie)
//        }else {
//            response
//        }
//    }
//}