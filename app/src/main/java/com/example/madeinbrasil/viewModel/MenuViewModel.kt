package com.example.madeinbrasil.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.database.entities.season.SeasonFirebase
import com.example.madeinbrasil.repository.MenuRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class MenuViewModel: ViewModel() {
    private val repository by lazy {
        MenuRepository()
    }
    var user: MutableLiveData<User> = MutableLiveData()
    var midia: MutableLiveData<MutableList<DocumentSnapshot>> = MutableLiveData()
    var cast: MutableLiveData<MutableList<CastFirebase>> = MutableLiveData()
    var season: MutableLiveData<MutableList<SeasonFirebase>> = MutableLiveData()
    var genre: MutableLiveData<MutableList<GenreFirebase>> = MutableLiveData()
    var list = mutableListOf<DocumentSnapshot>()

    fun getUser() {
        viewModelScope.launch {
            user.postValue(repository.getUser().toObject(User::class.java))
        }
    }

    fun getMidia(id: Int) {
        viewModelScope.launch {
            repository.getMidia(id)?.let {
                list.add(it)
                midia.postValue(list)
            }?: run {
                midia.postValue(null)
            }
        }
    }

    fun getGenre() {
        viewModelScope.launch {
            genre.postValue(repository.getGenre().toObjects(GenreFirebase::class.java))
        }
    }
}