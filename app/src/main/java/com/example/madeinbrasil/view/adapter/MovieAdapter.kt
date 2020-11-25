package com.example.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ListItemBinding
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.view.model.Movie

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
            Glide.with(binding.root.context).load(movie.coverPhotoAddress).into(binding.cvImage)
            binding.root.findViewById<TextView>(R.id.filmNameRecyclerView).text = movie.name
        }
    }
}