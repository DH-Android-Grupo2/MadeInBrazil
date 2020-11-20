package com.example.madeinbrasil.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.`class`.Films

class ViewHolderFilm (viewItem: View): RecyclerView.ViewHolder(viewItem) {
    fun bind(film: Films, position: (Int) -> Unit) = with(itemView) {
        val imageCardView = findViewById<ImageView>(R.id.cvImage)

        Glide.with(itemView.context).load(film.img).into(imageCardView)
    }
}