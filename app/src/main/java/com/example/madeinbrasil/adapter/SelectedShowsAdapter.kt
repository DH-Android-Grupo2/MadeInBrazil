package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.databinding.SelectedCardsBinding
import com.example.madeinbrasil.model.customLists.ListMediaItem
import com.example.madeinbrasil.model.search.ResultSearch

class SelectedShowsAdapter(val onCloseClick: ((ListMediaItem) -> Unit)): RecyclerView.Adapter<SelectedShowsAdapter.ViewHolder>() {

    val list: MutableList<ListMediaItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SelectedCardsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    fun deleteItem(it: ListMediaItem) {
        list.remove(it)
        notifyDataSetChanged()
    }

    fun addItem(it: ListMediaItem) {
        list.add(it)
        notifyItemInserted(list.size - 1)
    }

    inner class ViewHolder(val binding: SelectedCardsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(show: ListMediaItem) = with(binding) {

            Glide.with(itemView.context).load(show.backdropPath).into(tvShowCover)
            tvShowName.text = show.title

            imDeleteShow.setOnClickListener {
                onCloseClick(show)
                list.remove(show)
                notifyItemRemoved(adapterPosition)
            }
        }
    }
}