package com.example.madeinbrasil.adapter

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.example.madeinbrasil.model.movieCredits.Cast

import kotlinx.android.synthetic.main.filmsseries_popup.*
import com.example.madeinbrasil.model.movieCredits.MovieCredits
class FilmsSeriesFromUserAdapter(
        private var filmList: List<Cast> = listOf(),
        private val onMovieClicked: (Cast?) -> Unit
): RecyclerView.Adapter<FilmsSeriesFromUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return FilmsSeriesFromUserAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }
    override fun onBindViewHolder(holder: FilmsSeriesFromUserAdapter.ViewHolder, position: Int) {
        holder.bind(filmList[position],onMovieClicked)
    }

    class ViewHolder(
            private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(cast: Cast?, onMovieClicked: (Cast?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                    .load(cast?.poster_path)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(cvImageActors)
            tvActorsName.text = cast?.title

            itemView.setOnClickListener {
                onMovieClicked(cast)
            }

        }

    }
}