package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.database.entities.favorites.FavoritesSerieDetailed

class FavoriteSerieAdapter(
        private val listFavorites: List<FavoritesSerieDetailed>
): RecyclerView.Adapter<FavoriteSerieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteSerieAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    override fun onBindViewHolder(holder: FavoriteSerieAdapter.ViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    class ViewHolder(
            private val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoritesSerieDetailed) = with(binding) {
            Glide.with(itemView.context).load(favorite.posterPath).into(cvImageCard)
            tvNameRecyclerView.text = favorite.name
        }
    }
}