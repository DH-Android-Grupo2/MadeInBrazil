package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.databinding.SelectedCardsBinding
import com.example.madeinbrasil.model.search.ResultSearch

class SelectedShowsAdapter(): RecyclerView.Adapter<SelectedShowsAdapter.ViewHolder>() {

    val list: MutableList<Any> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SelectedCardsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun deleteItem(it: Any) {
        list.remove(it)
        notifyDataSetChanged()
    }

    fun addItem(it: Any) {
        list.add(it)
        notifyItemInserted(list.size - 1)
    }

    inner class ViewHolder(val binding: SelectedCardsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Any) = with(binding) {
            when(show) {
                is Result -> {
                    Glide.with(itemView.context).load(show.backdropPath).into(tvShowCover)
                    tvShowName.text = show.title
                }
                is ResultSearch -> {
                    Glide.with(itemView.context).load(show.backdropPath).into(tvShowCover)
                    tvShowName.text = show.name
                }
            }
        }
    }
}