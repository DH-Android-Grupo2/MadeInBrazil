package com.example.madeinbrasil.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.`class`.Series
import kotlinx.android.synthetic.main.main_cards.view.*

class ViewHolderSeries(view: View): RecyclerView.ViewHolder(view) {
    fun bind(serie: Series) = with(itemView) {
        val image = findViewById<ImageView>(R.id.cvImage)

        Glide.with(itemView.context).load(serie.img).into(image)
    }
}