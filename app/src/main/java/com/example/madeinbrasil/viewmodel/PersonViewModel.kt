package com.example.madeinbrasil.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.MovieDetailedBusiness
import com.example.madeinbrasil.business.PersonBusiness
import com.example.madeinbrasil.model.people.Person
import com.example.madeinbrasil.model.result.MovieDetailed
import kotlinx.coroutines.launch

class PersonViewModel : ViewModel() {
    val personSucess: MutableLiveData<Person> = MutableLiveData()
    val personError: MutableLiveData<String> = MutableLiveData()

    private val detailed by lazy {
        PersonBusiness()
    }

    fun getPerson(personId: Int?) {
        viewModelScope.launch {
            when(val response = personId?.let { detailed.getPerson(it) }) {
                is ResponseAPI.Success -> {
                    personSucess.postValue(
                            response.data as Person
                    )
                }
                is ResponseAPI.Error -> {
                    personError.postValue(response.message)
                }
            }
        }
    }
}