package com.dh.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dh.madeinbrasil.api.ResponseAPI
import com.dh.madeinbrasil.business.MovieDetailedBusiness
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.database.entities.cast.CastFirebase
import com.dh.madeinbrasil.database.entities.genre.GenreFirebase
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.model.classe.CommentFirebase
import com.dh.madeinbrasil.model.result.MovieDetailed
import com.dh.madeinbrasil.model.upcoming.Result
import com.dh.madeinbrasil.view.activity.MenuActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.dh.madeinbrasil.repository.MovieDetailedRepository
import kotlinx.coroutines.launch

class MovieDetailedViewModel(application: Application): AndroidViewModel(application) {
    val movieSucess: MutableLiveData<MovieDetailed> = MutableLiveData()
    val movieError: MutableLiveData<List<MidiaFirebase>> = MutableLiveData()
    val midia: MutableLiveData<MutableList<MidiaFirebase?>> = MutableLiveData()
    val cast: MutableLiveData<MutableList<DocumentSnapshot>> = MutableLiveData()
    val searchDB: MutableLiveData<List<Result>> = MutableLiveData()
    var listMidia = mutableListOf<MidiaFirebase?>()
    var listCast = mutableListOf<DocumentSnapshot>()
    private val detailed by lazy {
        MovieDetailedBusiness(application)
    }

    private val detailedRepository by lazy {
        MovieDetailedRepository(application)
    }

    val onGetComments: MutableLiveData<MutableList<CommentFirebase?>> = MutableLiveData()

    fun getMovie(movieId: Int?) {
        viewModelScope.launch {
            when(val response = movieId?.let { detailed.getMovie(it) }) {
                is ResponseAPI.Success -> {
                    movieSucess.postValue(response.data as MovieDetailed)
                }
                is ResponseAPI.Error -> {
//                    movieError.postValue(midiaDB.getMidia())
                    movieError.postValue(MenuActivity.MIDIA)
                }
            }
        }
    }

    fun getMidiaFireBase(id: Int) {
        viewModelScope.launch {
            detailed.getMidiaFireBase(id)?.let {
                listMidia.add(it.toObject(MidiaFirebase::class.java))
                midia.postValue(listMidia)
            }?: run {
                midia.postValue(null)
            }
        }
    }

    fun getCast(id: Int) {
        viewModelScope.launch {
            detailed.getCast(id)?.let {
                listCast.add(it)
                cast.postValue(listCast)
            }?: run {
                cast.postValue(null)
            }
        }
    }

    fun setMidiaFireBase(id: Int, infos: MidiaFirebase) {
        viewModelScope.launch {
            detailed.setMidiaFireBase(id, infos)
        }
    }

    fun setGenreFireBase(id: Int, infos: GenreFirebase) {
        viewModelScope.launch {
            detailed.setGenreFireBase(id, infos)
        }
    }

    fun setCastFireBase(id: Int, infos: CastFirebase) {
        viewModelScope.launch {
            detailed.setCastFireBase(id, infos)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            detailed.updateUser(user)
        }
    }

    fun getSearchDB() {
        viewModelScope.launch {
            searchDB.postValue(detailed.getSearchDB())
        }
    }
//    fun postComment(comment: CommentFirebase){
//        viewModelScope.launch {
//            detailedRepository.postComment(hashMap,id)
//       }
//   }
//
//    fun getComment(id: Int){
//        viewModelScope.launch {
//           val docs =  detailedRepository.getComment(id)
//            onGetComments.postValue(docs)
//        }
//    }
}