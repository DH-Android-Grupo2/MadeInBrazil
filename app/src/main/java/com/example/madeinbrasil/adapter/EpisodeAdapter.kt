package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.RecyclerviewSeasonsBinding
import com.example.madeinbrasil.model.seasons.Episode

class EpisodeAdapter(
    private var episodelist: List<Episode>
): RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewSeasonsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return episodelist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(episodelist[position])
    }

    class ViewHolder(
        val binding: RecyclerviewSeasonsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) = with(binding) {
            Glide.with(itemView.context)
                .load(episode?.still_path)
                .placeholder(R.drawable.made_in_brasil_logo)
                .into(cvImageSeason)

            tvSeasonName.text = episode?.name
            tvSeasonOverview.text = episode?.overview
        }
    }
}