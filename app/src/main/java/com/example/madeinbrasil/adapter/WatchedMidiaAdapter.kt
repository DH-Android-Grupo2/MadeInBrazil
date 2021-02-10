package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.databinding.MainCardsBinding

class WatchedMidiaAdapter(
        private val listSerie: List<MidiaFirebase>,
        private val onClickWatched: (MidiaFirebase) -> Unit
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
        holder.bind(listSerie[position], onClickWatched)
    }

    class ViewHolder(
            val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(watched: MidiaFirebase, onClick: (MidiaFirebase) -> Unit) = with(binding) {
            Glide.with(itemView.context).load(watched.posterPath).into(cvImageCard)
            tvNameMedia.text = watched.name

            itemView.setOnClickListener {
                onClick(watched)
            }
        }
    }
}