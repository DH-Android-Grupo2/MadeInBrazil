package com.example.madeinbrasil.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.repository.RegisterRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    var register: MutableLiveData<FirebaseUser?> = MutableLiveData()
    private val repository by lazy {
        RegisterRepository()
    }

    fun createNewUser(email: String, password: String, user: User,context: Context) {
        viewModelScope.launch {
            repository.createNewUser(email, password, user, context)?.let {
                register.postValue(it)
            }?: run {
                register.postValue(null)
            }
        }
    }
}