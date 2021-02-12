package com.example.madeinbrasil.view.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.databinding.MainCardsSelectionBinding
import com.example.madeinbrasil.databinding.MyListCardItemBinding
import com.example.madeinbrasil.model.customLists.ListMediaItem
import com.example.madeinbrasil.model.customLists.firebase.Media

class ListDetailsAdapter(val onItemClick: (String) -> Unit) : RecyclerView.Adapter<ListDetailsAdapter.ViewHolder>() {

    lateinit var list: MutableList<Media>
    var onMediaClick: ((Int) -> Unit)? = null
    var onMediaLongClick: ((Int) -> Unit)? = null
    val selectedItems: MutableList<String> = mutableListOf()
    var selectedPositions = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailsAdapter.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(MyListCardItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListDetailsAdapter.ViewHolder, position: Int) {
        holder.bind(list[position], selectedItems)
        holder.itemView.setOnClickListener {
            if (selectedItems.size > 0)
                onMediaClick?.invoke(holder.adapterPosition)
        }
        holder.itemView.setOnLongClickListener {
            onMediaLongClick?.invoke(holder.adapterPosition)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = list.size

    fun tooglePosition(position: Int) {
        if (selectedPositions[position, false]) {
            selectedPositions.delete(position)
            selectedItems.remove(list[position].id)
        } else {
            selectedPositions.put(position, true)
            selectedItems.add(list[position].id)
        }
        notifyItemChanged(position)
    }

    fun deleteMedia() {
        list.removeAll(
            list.filter {
                selectedItems.contains(it.id)
            }
        )
        selectedItems.clear()
        selectedPositions.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: MyListCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaItem: Media, selectedItems: List<String>) = with(binding) {
            Glide.with(binding.root).load(mediaItem.posterPath ?: mediaItem.backdropPath).placeholder(R.drawable.logo_made_in_brasil).into(cvImageCard)
            tvNameMedia.text = mediaItem.title ?: mediaItem.name

            if (selectedItems.contains(mediaItem.id)) {
                tvSelectionIcon.visibility = View.VISIBLE
                cardViewStroke.strokeWidth = 4
            } else {
                tvSelectionIcon.visibility = View.GONE
                cardViewStroke.strokeWidth = 0
            }
        }
    }
}
