package com.example.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.model.customLists.ListMediaItem

class ListDetailsAdapter(val list: List<ListMediaItem>,
                         val onItemClick: (Long) -> Unit) : RecyclerView.Adapter<ListDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailsAdapter.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(MainCardsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListDetailsAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: MainCardsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaItem: ListMediaItem) = with(binding) {
            Glide.with(binding.root).load(mediaItem.backdropPath).placeholder(R.drawable.logo_made_in_brasil).into(cvImageCard)
            tvNameMedia.text = mediaItem.title
        }
    }
}
