package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.database.entities.favorites.MidiaWithFavorites
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.watched.MidiaWithWatched
import com.example.madeinbrasil.databinding.MainCardsBinding

class FavoriteMidiaAdapter(
        private val listFavorites: List<MidiaWithFavorites>
): RecyclerView.Adapter<FavoriteMidiaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    class ViewHolder(
            private val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: MidiaWithFavorites) = with(binding) {
            when(favorite.midia.midiaType) {
                1 -> {
                    Glide.with(itemView.context).load(favorite.midia.posterPath).into(cvImageCard)
                    tvNameMedia.text = favorite.midia.title
                }
                2 -> {
                    Glide.with(itemView.context).load(favorite.midia.posterPath).into(cvImageCard)
                    tvNameMedia.text = favorite.midia.name
                }
            }
        }
    }
}