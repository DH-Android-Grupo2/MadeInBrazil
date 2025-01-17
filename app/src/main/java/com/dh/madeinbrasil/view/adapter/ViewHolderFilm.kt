package com.dh.madeinbrasil.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.model.classe.Films
import kotlinx.android.synthetic.main.main_cards.view.*

class ViewHolderFilm (viewItem: View): RecyclerView.ViewHolder(viewItem) {
    fun bind(film: Films, position: (Int) -> Unit) = with(itemView) {
        val imageCardView = findViewById<ImageView>(R.id.cvImageCard)
        val nameFilmCardView = findViewById<TextView>(R.id.tvNameMedia)

        Glide
            .with(itemView.context)
            .load(film.img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageCardView)


        nameFilmCardView.text = film.name

        vgMainCard.setOnClickListener {
            position(this@ViewHolderFilm.adapterPosition)
        }
    }
}