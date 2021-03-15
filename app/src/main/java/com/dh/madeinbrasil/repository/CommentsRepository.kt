package com.dh.madeinbrasil.repository

import com.dh.madeinbrasil.model.classe.CommentFirebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CommentsRepository {

    private val firebaseComments by lazy{
        Firebase.firestore
    }

    suspend fun postComment(hashMap: Any,id: Int) {
        firebaseComments.collection("commentsByMedia").document(id.toString()).collection("comments").document().set(hashMap).await()
    }

    suspend fun getComment(id: Int): MutableList<CommentFirebase?> {
        val docRef = firebaseComments.collection("commentsByMedia").document(id.toString()).collection("comments")

        return try {
            val documments =  docRef.get().await()
            val commentList = mutableListOf<CommentFirebase?>()
            documments.toObjects(CommentFirebase::class.java)
            documments.forEach {
                commentList.add(it.toObject())
            }
            return commentList
        } catch (e:Exception){
            return mutableListOf()
        }

    }
}
