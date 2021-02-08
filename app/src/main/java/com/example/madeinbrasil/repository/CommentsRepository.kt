package com.example.madeinbrasil.repository

import com.example.madeinbrasil.model.classe.CommentFirebase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommentsRepository {

    private val firebaseFirestore by lazy{
        Firebase.firestore
    }

    var listComments = mutableListOf<CommentFirebase>()



}
