package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCastRecyclerviewBinding

import kotlinx.android.synthetic.main.filmsseries_popup.*
import com.dh.madeinbrasil.model.upcoming.Result

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