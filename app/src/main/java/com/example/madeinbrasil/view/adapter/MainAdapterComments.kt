package com.example.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.`class`.Comments

class MainAdapterComments(
    private val commentsList: List<Comments>
): RecyclerView.Adapter<ViewHolderComments>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComments {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_comment_recyclerview, parent, false)
        return ViewHolderComments(view)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    override fun onBindViewHolder(holder: ViewHolderComments, position: Int) {
        holder.bind(commentsList[position])
    }
}