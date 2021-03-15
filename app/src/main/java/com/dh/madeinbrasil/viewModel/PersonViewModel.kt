package com.dh.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.business.PersonBusiness
import com.dh.madeinbrasil.model.people.Person
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