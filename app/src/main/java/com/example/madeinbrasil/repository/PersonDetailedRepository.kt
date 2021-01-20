package com.example.madeinbrasil.repository

import android.content.Context
import com.example.madeinbrasil.api.APIService
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.cast.MidiaCastCrossRef

class PersonDetailedRepository(context: Context){

    private val peopleDao by lazy {
        MadeInBrazilDatabase.getDatabase(context).peopleDao()
    }
    suspend fun getPerson(personId: Int): ResponseAPI {
        return try {
            val response = APIService.tmdbApiPerson.personDetails(personId)

            if (response.isSuccessful) {
                ResponseAPI.Success(response.body())
            } else {
                if (response.code() == 404) {
                    ResponseAPI.Error("Dado não encontrado")
                } else {
                    ResponseAPI.Error("Erro ao carregar os dados")
                }
            }
        } catch (exception: Exception) {
            ResponseAPI.Error("Erro ao carregar os dados")
        }
    }

//    suspend fun insertPeople(people: CastEntity) {
//        peopleDao.insertPeople(people)
//    }
suspend fun insertPeople(people: MidiaCastCrossRef) {
    peopleDao.insertPeople(people)
}
}