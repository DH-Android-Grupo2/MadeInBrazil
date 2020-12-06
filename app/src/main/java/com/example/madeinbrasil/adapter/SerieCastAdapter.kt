package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.example.madeinbrasil.model.serieCredits.Cast

class SerieCastAdapter(
        private val listSerieCast: List<Cast> = listOf()
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
        holder.bind(listSerieCast[position])
    }

    class ViewHolder(
            private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(serieCast: Cast?) = with(binding) {
            Glide.with(itemView.context)
                    .load(serieCast?.profilePath)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(cvImageActors)

            tvActorsName.text = serieCast?.name
            tvActorsCharacter.text = serieCast?.character
        }
    }
}