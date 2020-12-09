package com.example.madeinbrasil.business

import android.app.Person
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.repository.PersonDetailedRepository

class PersonBusiness {
    private val repository: PersonDetailedRepository by lazy {
        PersonDetailedRepository()
    }

    suspend fun getPerson(personId: Int): ResponseAPI {
        val response = repository.getPerson(personId)
        return if (response is ResponseAPI.Success) {
            val person = response.data as com.example.madeinbrasil.model.people.Person
//            person.movie_credits?.cast?.filter { it.original_language.equals("pt") }
            person.movie_credits?.cast?.forEach {
                it.posterPath = it.posterPath?.getFullImagePath()
            }
            person.tv_credits?.cast?.forEach {
                it.posterPath = it.posterPath?.getFullImagePath()
            }
            ResponseAPI.Success(person)
        } else {
            response
        }
    }
}