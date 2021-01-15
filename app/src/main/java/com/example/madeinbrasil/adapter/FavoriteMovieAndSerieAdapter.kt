package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.database.entities.favorites.FavoritesMovieDetailed
import com.example.madeinbrasil.database.entities.favorites.FavoritesSerieDetailed
import com.example.madeinbrasil.databinding.MainCardsBinding

class FavoriteMovieAndSerieAdapter (
        private val listSerie: List<FavoritesSerieDetailed>,
        private val listMovie: List<FavoritesMovieDetailed>
): RecyclerView.Adapter<FavoriteMovieAndSerieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieAndSerieAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listSerie.size
    }

    override fun onBindViewHolder(holder: FavoriteMovieAndSerieAdapter.ViewHolder, position: Int) {
        holder.bind(listSerie[position], listMovie[position])
    }

    class ViewHolder(
            private val binding: MainCardsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(serie: FavoritesSerieDetailed, movie: FavoritesMovieDetailed) = with(binding) {
            when(serie.idSerie) {
                2 -> {
                    Glide.with(itemView.context).load(serie.posterPath).into(cvImageCard)
                    tvNameRecyclerView.text = serie.name
                }
                else -> {
                    Glide.with(itemView.context).load(movie.posterPath).into(cvImageCard)
                    tvNameRecyclerView.text = movie.title
                }
            }
        }
    }
}