package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.business.CustomListBusiness
import com.example.madeinbrasil.model.customLists.CustomList
import com.example.madeinbrasil.model.customLists.ListWithMediaUni
import com.example.madeinbrasil.model.customLists.relation.ListWithMedia
import kotlinx.coroutines.launch

class CustomListViewModel(application: Application): AndroidViewModel(application) {

    val lists: MutableLiveData<List<ListWithMedia>> = MutableLiveData()
    val uniLists: MutableLiveData<List<ListWithMediaUni>> = MutableLiveData()
    val uniList: MutableLiveData<ListWithMediaUni> = MutableLiveData()
    val customLists: MutableLiveData<List<CustomList>> = MutableLiveData()
    val customListMovieIds: MutableLiveData<List<Long>> = MutableLiveData()

    private val customListBusinnes: CustomListBusiness by lazy {
        CustomListBusiness(application)
    }

    fun createCustomList(list: ListWithMedia) {
        viewModelScope.launch {  customListBusinnes.createList(list) }
    }

    fun getLists() {
        viewModelScope.launch {
            lists.postValue(
            customListBusinnes.getLists()
            )
        }
    }

    fun getListsUni() {
        viewModelScope.launch {
            uniLists.postValue(
                    customListBusinnes.getListsUni()
            )
        }
    }

    fun getListUni(id: Long) {
        viewModelScope.launch {
            uniList.postValue(
                    customListBusinnes.getListUni(id)
            )
        }
    }

    fun getCustomLists() {
        viewModelScope.launch {
            customLists.postValue(
                    customListBusinnes.getCustomLists()
            )
        }
    }

    fun getCustomListMovieIds(listId: Long) {
        viewModelScope.launch {
            customListMovieIds.postValue(
                customListBusinnes.getCustomListMovieIds(listId)
            )
        }
    }

    fun deleteMoviesFromList(listId: Long, moviesId: List<Long>) {
        viewModelScope.launch {
            customListBusinnes.deleteMoviesFromList(listId, moviesId)
        }
    }

    fun deleteSeriesFromList(listId: Long, seriesId: List<Long>) {
        viewModelScope.launch {
            customListBusinnes.deleteSeriesFromList(listId, seriesId)
        }
    }

}