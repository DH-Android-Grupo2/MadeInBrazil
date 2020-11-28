package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.UserFavoritesRecyclerviewBinding
import com.example.madeinbrasil.model.upcoming.Result

class HomeAdapter (
    private val onMovieClicked: (Result?) -> Unit
) : PagedListAdapter<Result, HomeAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UserFavoritesRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClicked)
    }

    class ViewHolder(
        private val binding: UserFavoritesRecyclerviewBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(movie: Result?, onMovieClicked: (Result?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load(movie?.posterPath)
                .placeholder(R.drawable.made_in_brasil_logo)
                .into(cvImageFavoritesProfile)
            tvNameFavoritesProfile.text = movie?.title

            itemView.setOnClickListener {
                onMovieClicked(movie)
            }
        }
    }
}