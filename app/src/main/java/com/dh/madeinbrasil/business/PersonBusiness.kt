package com.dh.madeinbrasil.business

import android.content.Context
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.extensions.getFirst4Chars
import com.dh.madeinbrasil.extensions.getFullImagePath
import com.dh.madeinbrasil.model.people.Person
import com.dh.madeinbrasil.repository.PersonDetailedRepository

class PersonBusiness(val context: Context) {
    private val repository: PersonDetailedRepository by lazy {
        PersonDetailedRepository(context)
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