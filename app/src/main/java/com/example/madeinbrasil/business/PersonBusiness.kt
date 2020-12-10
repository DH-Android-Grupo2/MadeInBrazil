package com.example.madeinbrasil.business

import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.extensions.getFirst4Chars
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.people.Person
import com.example.madeinbrasil.repository.PersonDetailedRepository
import java.util.*

class PersonBusiness {
    private val repository: PersonDetailedRepository by lazy {
        PersonDetailedRepository()
    }

    suspend fun getPerson(personId: Int): ResponseAPI {
        val response = repository.getPerson(personId)
        return if (response is ResponseAPI.Success) {
            val person = response.data as Person

            if(person.biography == "") {
                person.biography = "Biografia não encontrada"
            }

            person.birthday?.let {
                person.birthday = it.getFirst4Chars()
            }

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