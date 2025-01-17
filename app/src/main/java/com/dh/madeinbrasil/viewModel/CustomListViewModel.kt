package com.dh.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dh.madeinbrasil.api.firebase.FirebaseResponse
import com.dh.madeinbrasil.business.CustomListBusiness
import com.dh.madeinbrasil.model.customLists.ListWithMedia
import com.dh.madeinbrasil.model.customLists.firebase.CustomList
import com.dh.madeinbrasil.model.customLists.firebase.Media
import kotlinx.coroutines.launch

class CustomListViewModel(application: Application): AndroidViewModel(application) {

//    val lists: MutableLiveData<List<ListWithMedia>> = MutableLiveData()
//    val uniLists: MutableLiveData<List<ListWithMediaUni>> = MutableLiveData()
//    val uniList: MutableLiveData<ListWithMediaUni> = MutableLiveData()
//    val customLists: MutableLiveData<List<CustomList>> = MutableLiveData()
//    val customListMovieIds: MutableLiveData<List<Long>> = MutableLiveData()
//

    val getListsSuccess: MutableLiveData<List<ListWithMedia>> = MutableLiveData()
    val getUserListsSuccess: MutableLiveData<List<CustomList>> = MutableLiveData()
    val listSucess: MutableLiveData<String> = MutableLiveData()
    val listFailure: MutableLiveData<String> = MutableLiveData()

    private val customListBusinnes: CustomListBusiness by lazy {
        CustomListBusiness()
    }

    fun createList(customList: CustomList, mediaList: List<Media>) {
        viewModelScope.launch {
            when(val response = customListBusinnes.createList(customList, mediaList)) {
                is FirebaseResponse.OnSucess ->
                    listSucess.postValue(
                        ""
                    )
                is FirebaseResponse.OnFailure ->
                    listFailure.postValue(
                        response.message
                    )
            }
        }
    }

    fun getUserExceptionLists() {
        viewModelScope.launch {
            when(val response = customListBusinnes.getUserExceptionLists()) {
                is FirebaseResponse.OnSucess ->
                    getUserListsSuccess.postValue(
                            response.data as List<CustomList>
                    )
                is FirebaseResponse.OnFailure ->
                    listFailure.postValue(
                            response.message
                    )
            }
        }
    }

    fun getListWithMedia(all: Boolean) {
        viewModelScope.launch {
            when(val response = customListBusinnes.getListsWithMedia(all)) {
                is FirebaseResponse.OnSucess ->
                    getListsSuccess.postValue(
                        response.data as? List<ListWithMedia>
                    )
                is FirebaseResponse.OnFailure ->
                    listFailure.postValue(
                        response.message
                    )
            }
        }
    }

    fun addItemtoList(list: ListWithMedia, showType: String) {
        viewModelScope.launch {
            when(val response = customListBusinnes.addItemtoList(list, showType)) {
                is FirebaseResponse.OnSucess -> listSucess.postValue(
                        response.data as String
                )
                is FirebaseResponse.OnFailure -> listFailure.postValue(
                        response.message
                )
            }
        }
    }

    fun resetListMedia(listId: String, moviesId: List<String>, seriesId: List<String>) {
        viewModelScope.launch {
            when(val response = customListBusinnes.resetMediaList(listId, moviesId, seriesId)) {
                is FirebaseResponse.OnSucess ->
                    listSucess.postValue(
                            response.data as String
                    )
                is FirebaseResponse.OnFailure ->
                    listFailure.postValue(
                            response.message
                    )
            }
        }
    }

    fun updateList(list: ListWithMedia) {
        viewModelScope.launch {
            when(val response = customListBusinnes.updateList(list)) {
                is FirebaseResponse.OnSucess ->
                    listSucess.postValue(
                            response.data as String
                    )
                is FirebaseResponse.OnFailure ->
                    listFailure.postValue(
                            response.message
                    )
            }
        }
    }

    fun deleteLists(selectedLists: List<String>) {
        viewModelScope.launch {
            when(val response = customListBusinnes.deleteLists(selectedLists)) {
                is FirebaseResponse.OnSucess ->
                    listSucess.postValue(
                            response.data as String
                    )
                is FirebaseResponse.OnFailure ->
                    listFailure.postValue(
                            response.message
                    )
            }
        }
    }
//
//    fun createCustomList(list: ListWithMedia) {
//        viewModelScope.launch {  customListBusinnes.createList(list) }
//    }
//
//    fun getLists() {
//        viewModelScope.launch {
//            lists.postValue(
//            customListBusinnes.getLists()
//            )
//        }
//    }
//
//    fun getListsUni() {
//        viewModelScope.launch {
//            uniLists.postValue(
//                    customListBusinnes.getListsUni()
//            )
//        }
//    }
//
//    fun getListUni(id: Long) {
//        viewModelScope.launch {
//            uniList.postValue(
//                    customListBusinnes.getListUni(id)
//            )
//        }
//    }
//
//    fun getCustomLists() {
//        viewModelScope.launch {
//            customLists.postValue(
//                    customListBusinnes.getCustomLists()
//            )
//        }
//    }
//
//    fun getCustomListMovieIds(listId: Long) {
//        viewModelScope.launch {
//            customListMovieIds.postValue(
//                customListBusinnes.getCustomListMovieIds(listId)
//            )
//        }
//    }
//
//    fun deleteMoviesFromList(listId: Long, moviesId: List<Long>) {
//        viewModelScope.launch {
//            customListBusinnes.deleteMoviesFromList(listId, moviesId)
//        }
//    }
//
//    fun deleteSeriesFromList(listId: Long, seriesId: List<Long>) {
//        viewModelScope.launch {
//            customListBusinnes.deleteSeriesFromList(listId, seriesId)
//        }
//    }

}