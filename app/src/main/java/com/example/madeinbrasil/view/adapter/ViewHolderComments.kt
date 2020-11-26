package com.example.madeinbrasil.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.`class`.Comments

class ViewHolderComments(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(comment: Comments)= with(itemView) {
        val profileImage = findViewById<ImageView>(R.id.ivProfileComment)
        val profileIName = findViewById<TextView>(R.id.tvProfileName)
        val profileComment = findViewById<TextView>(R.id.tvComment)

        Glide.with(this).load(comment.img).into(profileImage)
        profileIName.text = comment.name
        profileComment.text = comment.comment
    }
}