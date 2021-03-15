package com.dh.madeinbrasil.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.repository.FragmentsRepository
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