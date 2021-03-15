package com.dh.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.model.classe.CommentFirebase

class MainAdapterComments(
    private val commentsList: MutableList<CommentFirebase?> = mutableListOf()
): RecyclerView.Adapter<MainAdapterComments.ViewHolderComments>() {

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



    class ViewHolderComments(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(comment: CommentFirebase?)= with(itemView) {
            val profileImage = findViewById<ImageView>(R.id.ivProfileComment)
            val profileIName = findViewById<TextView>(R.id.tvProfileName)
            val profileComment = findViewById<TextView>(R.id.tvComment)

            Glide.with(this).load(comment?.userImage).placeholder(R.drawable.profile_photo).into(profileImage)
            profileIName.text = comment?.userName
            profileComment.text = comment?.commentText
        }
    }
}