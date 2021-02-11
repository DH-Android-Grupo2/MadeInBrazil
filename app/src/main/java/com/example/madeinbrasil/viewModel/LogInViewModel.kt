package com.example.madeinbrasil.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.repository.LogInRepository
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LogInViewModel: ViewModel() {
    var login: MutableLiveData<FirebaseUser?> = MutableLiveData()
//    var loginGoogle: MutableLiveData<FirebaseUser?> = MutableLiveData()
    private val repository by lazy {
        LogInRepository()
    }

    fun signInAuthentication(email: String, password: String,context: Context) {
        viewModelScope.launch {
            repository.signInAuthentication(email, password,context)?.let {
                login.postValue(it)
            }?: run {
                login.postValue(null)
            }
        }
    }

    fun logInWithGoogle(idToken: String?, user: User?) {
        viewModelScope.launch {
            repository.logInWithGoogle(idToken, user)?.let {
//                loginGoogle.postValue(it)
                login.postValue(it)
            }?: run {
                login.postValue(null)
//                loginGoogle.postValue(null)
            }
        }
    }

    fun logInWithFacebook(idToken: AccessToken, user: User?) {
        viewModelScope.launch {
            repository.logInWithFacebook(idToken, user)?.let {
                login.postValue(it)
            }?: run {
                login.postValue(null)
            }
        }
    }
}