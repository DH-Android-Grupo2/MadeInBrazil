package com.example.madeinbrasil.api

import com.example.madeinbrasil.utils.Constants.Api.BASE_URL_v3
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {

    fun getTMDbApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_v3)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
    }
}