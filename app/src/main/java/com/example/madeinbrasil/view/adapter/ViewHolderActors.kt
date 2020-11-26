package com.example.madeinbrasil.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.`class`.Actors
import kotlinx.android.synthetic.main.main_cast_recyclerview.view.*

class ViewHolderActors(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(actors: Actors) = with(itemView) {
        Glide.with(itemView.context)
            .load(actors.img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(cvImageActors)

        tvActorsName.text = actors.name
    }
}