package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCardsSelectionBinding
import com.dh.madeinbrasil.model.search.ResultSearch

class SelectSerieAdapter(var selectedItems: MutableList<String>) : PagedListAdapter<ResultSearch, SelectSerieAdapter.ViewHolder>(ResultSearch.DIFF_CALLBACK)   {

    var onItemClick: ((ResultSearch) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsSelectionBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serie = getItem(position)
        serie?.let {
            holder.bind(serie, selectedItems)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(serie)
                tooglePosition(serie)
                notifyItemChanged(position)
            }
        }
    }

    private fun tooglePosition(serie: ResultSearch?) {
        serie?.let {
            if(selectedItems.contains(it.id.toString()))
                selectedItems.remove(it.id.toString())
            else
                selectedItems.add(it.id.toString())
        }
    }

    class ViewHolder(
            val binding: MainCardsSelectionBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: ResultSearch?, selectedItems: List<String>) = with(binding) {
            serie?.let {

                Glide.with(itemView.context)
                        .load(serie.posterPath)
                        .placeholder(R.drawable.logo_made_in_brasil)
                        .into(cvImageCard)

                tvNameMedia.text = serie.name

                if(selectedItems.contains(serie.id.toString())) {
                    tvSelectionIcon.setImageResource(R.drawable.remove_item)
                    tvSelectionCover.visibility = View.INVISIBLE
                }
                else {
                    tvSelectionIcon.setImageResource(R.drawable.add_item)
                    tvSelectionCover.visibility = View.VISIBLE
                }
            }
        }
    }
}