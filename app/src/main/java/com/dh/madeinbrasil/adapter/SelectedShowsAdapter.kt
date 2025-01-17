package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dh.madeinbrasil.databinding.SelectedCardsBinding
import com.dh.madeinbrasil.model.customLists.firebase.Media

class SelectedShowsAdapter(val onCloseClick: ((Media) -> Unit)): RecyclerView.Adapter<SelectedShowsAdapter.ViewHolder>() {

    var list: MutableList<Media> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SelectedCardsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    fun deleteItem(it: Media) {
        list.remove(it)
        notifyDataSetChanged()
    }

    fun addItem(it: Media) {
        list.add(it)
        notifyItemInserted(list.size - 1)
    }

    inner class ViewHolder(val binding: SelectedCardsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Media) = with(binding) {

            Glide.with(itemView.context).load(show.backdropPath).into(tvShowCover)
            tvShowName.text = show.name ?: show.title

            imDeleteShow.setOnClickListener {
                onCloseClick(show)
                list.remove(show)
                notifyItemRemoved(adapterPosition)
            }
        }
    }
}