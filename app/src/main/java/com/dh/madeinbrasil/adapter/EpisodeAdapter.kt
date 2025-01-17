package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.RecyclerviewSeasonsBinding
import com.dh.madeinbrasil.model.seasons.Episode

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
                    .load(episode.still_path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .into(cvImageSeason)

            tvSeasonName.text = episode.name
            tvSeasonOverview.text = episode.overview
        }
    }
}