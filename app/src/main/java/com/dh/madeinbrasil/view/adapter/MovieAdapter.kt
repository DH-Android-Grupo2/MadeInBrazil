package com.dh.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCardsBinding
import com.dh.madeinbrasil.view.model.Movie

class MovieAdapter(val list: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(list[position])
    }

    inner class ViewHolder(
        private val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(binding.root.context).load(movie.coverPhotoAddress).into(binding.cvImageCard)
            binding.root.findViewById<TextView>(R.id.tvNameMedia).text = movie.name
        }
    }
}