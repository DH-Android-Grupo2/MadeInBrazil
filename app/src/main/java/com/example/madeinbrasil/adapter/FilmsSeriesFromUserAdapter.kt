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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.example.madeinbrasil.model.movieCredits.Cast

import kotlinx.android.synthetic.main.filmsseries_popup.*
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import com.example.madeinbrasil.model.upcoming.Result

class FilmsSeriesFromUserAdapter(
        private var filmList: List<Result> = listOf(),
        private val onMovieClicked: (Result?) -> Unit
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

        fun bind(cast: Result?, onMovieClicked: (Result?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                    .load(cast?.posterPath)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvImageActors)
            tvActorsName.text = cast?.title

            itemView.setOnClickListener {
                onMovieClicked(cast)
            }

        }

    }
}