package com.example.madeinbrasil.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.repository.FragmentsRepository
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val repository by lazy {
        FragmentsRepository()
    }

    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }
}