package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.watched.MidiaWithWatched
import com.example.madeinbrasil.databinding.MainCardsBinding

class WatchedMidiaAdapter(
        val listSerie: List<MidiaWithWatched>
): RecyclerView.Adapter<WatchedMidiaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listSerie.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSerie[position])
    }

    class ViewHolder(
            val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(watched: MidiaWithWatched) = with(binding) {
            when(watched.midia.midiaType) {
                1 -> {
                    Glide.with(itemView.context).load(watched.midia.posterPath).into(cvImageCard)
                    tvNameMedia.text = watched.midia.title
                }
                2 -> {
                    Glide.with(itemView.context).load(watched.midia.posterPath).into(cvImageCard)
                    tvNameMedia.text = watched.midia.name
                }
            }
        }
    }
}