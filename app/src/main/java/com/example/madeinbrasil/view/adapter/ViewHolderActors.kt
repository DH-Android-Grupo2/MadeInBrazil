package com.example.madeinbrasil.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.model.classe.Actors
import com.example.madeinbrasil.model.movieCredits.Cast
import kotlinx.android.synthetic.main.main_cast_recyclerview.view.*

class ViewHolderActors(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(actors: Cast) = with(itemView) {
        Glide.with(itemView.context)
            .load(actors.profilePath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(cvImageActors)

        tvActorsName.text = actors.name
    }
}