package com.example.madeinbrasil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madeinbrasil.model.classe.CommentFirebase
import com.example.madeinbrasil.repository.CommentsRepository
import kotlinx.coroutines.launch

class CommentViewModel(application: Application) : AndroidViewModel(application){

    private val commentRepository by lazy{
        CommentsRepository()
    }
    val onGetComments: MutableLiveData<MutableList<CommentFirebase?>> = MutableLiveData()

    fun postComment(hashMap: Any, id: Int){
        viewModelScope.launch {
            commentRepository.postComment(hashMap,id)
        }
    }

    fun getComment(id: Int){
        viewModelScope.launch {
            val docs =  commentRepository.getComment(id)
            onGetComments.postValue(docs)
        }
    }
}