package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsSelectionBinding
import com.example.madeinbrasil.model.search.ResultSearch

class SelectSerieAdapter(var selectedItems: MutableList<Int>) : PagedListAdapter<ResultSearch, SelectSerieAdapter.ViewHolder>(ResultSearch.DIFF_CALLBACK)   {

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
            if(selectedItems.contains(it.id))
                selectedItems.remove(it.id)
            else
                selectedItems.add(it.id)
        }
    }

    class ViewHolder(
            val binding: MainCardsSelectionBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: ResultSearch?, selectedItems: List<Int>) = with(binding) {
            serie?.let {

                Glide.with(itemView.context)
                        .load(serie.posterPath)
                        .placeholder(R.drawable.logo_made_in_brasil)
                        .into(cvImageCard)

                tvNameRecyclerView.text = serie.name

                if(selectedItems.contains(serie.id)) {
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