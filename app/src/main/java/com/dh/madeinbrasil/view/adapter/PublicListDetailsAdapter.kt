package com.dh.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MyListCardItemBinding
import com.dh.madeinbrasil.model.customLists.firebase.Media

class PublicListDetailsAdapter(val onItemClick: (Media) -> Unit): RecyclerView.Adapter<PublicListDetailsAdapter.ViewHolder>() {

    lateinit var list: MutableList<Media>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(MyListCardItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onItemClick)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: MyListCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaItem: Media, onClick: (Media) -> Unit) = with(binding) {
            Glide.with(binding.root).load(mediaItem.posterPath ?: mediaItem.backdropPath).placeholder(
                R.drawable.logo_made_in_brasil).into(cvImageCard)
            tvNameMedia.text = mediaItem.title ?: mediaItem.name

                tvSelectionIcon.visibility = View.GONE
                cardViewStroke.strokeWidth = 0

            itemView.setOnClickListener {
                onClick(mediaItem)
            }
        }
    }
}