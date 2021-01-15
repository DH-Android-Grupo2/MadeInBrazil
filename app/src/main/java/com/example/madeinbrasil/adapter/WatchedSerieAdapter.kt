package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.database.entities.watched.WatchedSerieDetailed
import com.example.madeinbrasil.databinding.MainCardsBinding

class WatchedSerieAdapter(
        val listSerie: List<WatchedSerieDetailed>
): RecyclerView.Adapter<WatchedSerieAdapter.ViewHolder>() {
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
        fun bind(serie: WatchedSerieDetailed) = with(binding) {
            Glide.with(itemView.context).load(serie.posterPath).into(cvImageCard)
            tvNameRecyclerView.text = serie.name
        }
    }
}