package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ItemStreamingBinding
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.result.Flatrate
import com.example.madeinbrasil.model.result.Watchproviders

class MovieStreamingAdapter(
        private var StreamingList: List<Flatrate> = listOf(),
        private val onWatchClicked: (Flatrate?) -> Unit
): RecyclerView.Adapter<MovieStreamingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStreamingBinding.inflate(layoutInflater, parent, false)
        return MovieStreamingAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return StreamingList.size
    }
    override fun onBindViewHolder(holder: MovieStreamingAdapter.ViewHolder, position: Int) {
        holder.bind(StreamingList[position],onWatchClicked)
    }

    class ViewHolder(
            private val binding: ItemStreamingBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(watchProviders: Flatrate?, onWatchClicked: (Flatrate?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                    .load(watchProviders?.logo_path)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(ivStreaming)

            itemView.setOnClickListener {
                onWatchClicked(watchProviders)
            }

        }

    }
}