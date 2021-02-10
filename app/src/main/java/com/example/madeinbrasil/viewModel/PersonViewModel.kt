package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.api.ResponseAPI
import com.example.madeinbrasil.business.PersonBusiness
import com.example.madeinbrasil.model.people.Person
import kotlinx.coroutines.launch

class PersonViewModel(application: Application): AndroidViewModel(application) {
    val personSucess: MutableLiveData<Person> = MutableLiveData()
    val personError: MutableLiveData<String> = MutableLiveData()

    private val detailed by lazy {
        PersonBusiness(application)
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