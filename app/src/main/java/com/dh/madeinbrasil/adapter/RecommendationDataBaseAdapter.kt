package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.databinding.MainCastRecyclerviewBinding

class RecommendationDataBaseAdapter (
//        private var recommendationList: List<RecommendationMidiaCrossRef> = listOf(),
        private var recommendationList: List<MidiaFirebase> = listOf(),
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

        fun bind(recommendation: MidiaFirebase) = with(binding) {
            Glide.with(itemView.context)
                    .load(recommendation.posterPath)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvImageActors)

            tvActorsName.text = recommendation.name
        }

    }
}