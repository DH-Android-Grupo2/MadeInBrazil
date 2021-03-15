package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCardsBinding
import com.dh.madeinbrasil.model.upcoming.Result

class FilmsAdapter(
    private val onMovieClicked: (Result?) -> Unit,
    private val onMovieLongClicked: (Result?) -> Unit
) : PagedListAdapter<Result, FilmsAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClicked, onMovieLongClicked)
    }

    class ViewHolder(
        private val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(movie: Result?, onMovieClicked: (Result?) -> Unit, onMovieLongClicked: (Result?) -> Unit) = with(binding) {

            Glide.with(itemView.context)
                .load(movie?.posterPath)
                .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cvImageCard)

            tvNameMedia.text = movie?.title

            itemView.setOnClickListener {
                onMovieClicked(movie)
            }

            itemView.setOnLongClickListener {
                onMovieLongClicked(movie)
                return@setOnLongClickListener true
            }
        }

    }

}