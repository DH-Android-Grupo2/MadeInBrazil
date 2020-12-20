package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ItemStreamingBinding
import com.example.madeinbrasil.databinding.RecyclerviewSeasonsBinding
import com.example.madeinbrasil.model.serieDetailed.Flatrate

class SerieStreamingAdapter(
    private val streamingList: List<Flatrate>,
    private val streamingLink: (Flatrate) -> Unit
): RecyclerView.Adapter<SerieStreamingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding= ItemStreamingBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return streamingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(streamingList[position], streamingLink)
    }

    class ViewHolder(
        private val binding: ItemStreamingBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(flatrate: Flatrate, streamingLink: (Flatrate) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load(flatrate.logoPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo_made_in_brasil)
                .into(ivStreaming)

            itemView.setOnClickListener {
                streamingLink(flatrate)
            }
        }
    }
}