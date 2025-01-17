package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.dh.madeinbrasil.model.movieCredits.Cast

class MovieCreditsAdapter(
    private var castList: List<Cast> = listOf(),
    private val onMovieClicked: (Cast?) -> Unit
): RecyclerView.Adapter<MovieCreditsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return MovieCreditsAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return castList.size
    }
    override fun onBindViewHolder(holder: MovieCreditsAdapter.ViewHolder, position: Int) {
        holder.bind(castList[position],onMovieClicked)
    }

    class ViewHolder(
        private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(cast: Cast?, onMovieClicked: (Cast?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load(cast?.profilePath)
                .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cvImageActors)
            tvActorsName.text = cast?.name
            tvActorsCharacter.text = cast?.character

            itemView.setOnClickListener {
                onMovieClicked(cast)
            }

        }

    }
}