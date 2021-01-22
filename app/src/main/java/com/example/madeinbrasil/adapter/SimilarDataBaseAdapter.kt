package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding

class SimilarDataBaseAdapter (
        private var recommendationList: List<SimilarMidiaCrossRef> = listOf(),
): RecyclerView.Adapter<SimilarDataBaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return SimilarDataBaseAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recommendationList.size
    }
    override fun onBindViewHolder(holder: SimilarDataBaseAdapter.ViewHolder, position: Int) {
        holder.bind(recommendationList[position])
    }

    class ViewHolder(
            private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(cast: SimilarMidiaCrossRef) = with(binding) {
            Glide.with(itemView.context)
                    .load(cast.posterPath)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvImageActors)

            tvActorsName.text = cast.name
        }

    }
}
