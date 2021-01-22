package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.example.madeinbrasil.model.upcoming.Result

class RecommendationDataBaseAdapter (
        private var recommendationList: List<RecommendationMidiaCrossRef> = listOf(),
): RecyclerView.Adapter<RecommendationDataBaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return RecommendationDataBaseAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recommendationList.size
    }
    override fun onBindViewHolder(holder: RecommendationDataBaseAdapter.ViewHolder, position: Int) {
        holder.bind(recommendationList[position])
    }

    class ViewHolder(
            private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(cast: RecommendationMidiaCrossRef) = with(binding) {
            Glide.with(itemView.context)
                    .load(cast.posterPath)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvImageActors)

            tvActorsName.text = cast.name
        }

    }
}