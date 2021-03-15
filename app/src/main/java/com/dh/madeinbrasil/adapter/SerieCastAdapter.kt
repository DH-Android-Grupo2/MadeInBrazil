package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.dh.madeinbrasil.model.serieDetailed.Cast

class SerieCastAdapter(
        private val listSerieCast: List<Cast> = listOf(),
        private val onClickCast: (Cast?) -> Unit
): RecyclerView.Adapter<SerieCastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listSerieCast.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSerieCast[position], onClickCast)
    }

    class ViewHolder(
            private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(serieCast: Cast?, onClickCast: (Cast?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                    .load(serieCast?.profilePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .into(cvImageActors)

            tvActorsName.text = serieCast?.name
            tvActorsCharacter.text = serieCast?.character

            itemView.setOnClickListener {
                onClickCast(serieCast)
            }
        }
    }
}