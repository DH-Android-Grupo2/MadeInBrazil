package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.database.entities.season.SeasonFirebase
import com.dh.madeinbrasil.databinding.RecyclerviewSeasonsBinding

class SeasonsDataBaseAdapter (
        val seasonsList: List<SeasonFirebase>,
): RecyclerView.Adapter<SeasonsDataBaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewSeasonsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return seasonsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(seasonsList[position])
    }

    class ViewHolder(
            val binding: RecyclerviewSeasonsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(season: SeasonFirebase) = with(binding) {
            Glide.with(itemView.context)
                    .load(season.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .into(cvImageSeason)

            tvSeasonName.text = season.name
            tvSeasonOverview.text = season.overview
        }
    }
}