package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.databinding.MainCastRecyclerviewBinding

class SimilarDataBaseAdapter (
        private var recommendationList: List<MidiaFirebase> = listOf()
): RecyclerView.Adapter<SimilarDataBaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recommendationList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recommendationList[position])
    }

    class ViewHolder(
            private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(similar: MidiaFirebase) = with(binding) {
            Glide.with(itemView.context)
                    .load(similar.posterPath)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvImageActors)

            tvActorsName.text = similar.name
        }

    }
}
