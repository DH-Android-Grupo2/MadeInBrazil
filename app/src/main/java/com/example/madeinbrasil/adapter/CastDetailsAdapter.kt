package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.example.madeinbrasil.model.movieCredits.Cast

class CastDetailsAdapter(
        private val castSerieList: List<Cast>
): RecyclerView.Adapter<CastDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return castSerieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castSerieList[position])
    }

    class ViewHolder(
        private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) = with(binding) {
            Glide.with(itemView.context)
                    .load(cast?.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(cvImageActors)
            tvActorsName.text = cast?.name
        }
    }
}