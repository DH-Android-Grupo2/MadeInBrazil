package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.RecyclerviewSeasonsBinding
import com.example.madeinbrasil.model.serieDetailed.Season

class SeasonsAdapter(
    val seasonsList: List<Season>,
    val onSeasonClicked: (Season?) -> Unit
): RecyclerView.Adapter<SeasonsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewSeasonsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return seasonsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(seasonsList[position], onSeasonClicked)
    }

    class ViewHolder(
        val binding: RecyclerviewSeasonsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(season: Season, onSeasonCLicked: (Season?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load(season?.posterPath)
                .placeholder(R.drawable.made_in_brasil_logo)
                .into(cvImageSeason)

            tvSeasonName.text = season?.name
            tvSeasonOverview.text = season?.overview

            itemView.setOnClickListener {
                onSeasonCLicked(season)
            }
        }
    }
}