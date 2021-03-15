package com.dh.madeinbrasil.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.model.classe.Series
import kotlinx.android.synthetic.main.main_cards.view.*

class ViewHolderSeries(view: View): RecyclerView.ViewHolder(view) {
    fun bind(serie: Series, position: (Int) -> Unit) = with(itemView) {
        val image = findViewById<ImageView>(R.id.cvImageCard)
        val nameSerieCardView = findViewById<TextView>(R.id.tvNameMedia)

        Glide.with(itemView.context)
            .load(serie.img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(image)
        nameSerieCardView.text = serie.name

        vgMainCard.setOnClickListener {
            position(this@ViewHolderSeries.adapterPosition)
        }
    }
}