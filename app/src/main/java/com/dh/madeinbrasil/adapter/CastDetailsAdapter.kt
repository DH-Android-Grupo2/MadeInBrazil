package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.dh.madeinbrasil.model.search.ResultSearch

class CastDetailsAdapter(
        private val castSerieList: List<ResultSearch>,
        private val onClickSerie: (ResultSearch) -> Unit
): RecyclerView.Adapter<CastDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return castSerieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castSerieList[position], onClickSerie)
    }

    class ViewHolder(
        private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: ResultSearch, onClickSerie: (ResultSearch) -> Unit) = with(binding) {

            Glide.with(itemView.context)
                    .load(cast.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .into(cvImageActors)
            tvActorsName.text = cast?.name

            itemView.setOnClickListener {
                onClickSerie(cast)
            }
        }
    }
}