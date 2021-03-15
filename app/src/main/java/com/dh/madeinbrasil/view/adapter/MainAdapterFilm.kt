package com.dh.madeinbrasil.view.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dh.madeinbrasil.databinding.FavoriteCardBinding
import com.dh.madeinbrasil.model.classe.Films

class MainAdapterFilm(
    private var filmsList: List<Films>,
    private var filmPosition: (Int) -> Unit): RecyclerView.Adapter<MainAdapterFilm.ViewHolder>()
{
    var selectedItems = SparseBooleanArray()
    var onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(FavoriteCardBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmsList[position])
        holder.itemView.setOnClickListener {
            if(selectedItems.size() > 0) {
                onItemClick?.invoke(position)
            }
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(position)
            return@setOnLongClickListener true
        }
    }

    inner class ViewHolder(val binding: FavoriteCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Films) = with(binding) {
            Glide.with(itemView.context).load(show.img).into(cvImageCard)
            tvShowName.text = show.name
        }
    }

}