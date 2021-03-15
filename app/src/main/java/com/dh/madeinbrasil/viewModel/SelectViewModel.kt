package com.dh.madeinbrasil.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.madeinbrasil.repository.SelectRepository
import kotlinx.coroutines.launch

class SelectViewModel: ViewModel() {
    private val repository by lazy {
        SelectRepository()
    }

    fun setUserGenres(genres: MutableList<String>) {
        viewModelScope.launch {
            repository.setUserGenres(genres)
        }
    }
}