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

}