package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.databinding.MainCardsBinding

class FavoriteMidiaAdapter(
        private val listFavorites: List<MidiaFirebase>,
        private val onClickFav: (MidiaFirebase) -> Unit
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
        holder.bind(listFavorites[position], onClickFav)
    }

    class ViewHolder(
            private val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: MidiaFirebase, onClick: (MidiaFirebase) -> Unit) = with(binding) {

            Glide.with(itemView.context).load(favorite.posterPath).into(cvImageCard)
            tvNameMedia.text = favorite.name

            itemView.setOnClickListener {
                onClick(favorite)
            }
        }
    }
}