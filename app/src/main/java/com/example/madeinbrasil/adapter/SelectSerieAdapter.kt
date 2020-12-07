package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsSelectionBinding
import com.example.madeinbrasil.model.search.ResultSearch

class SelectSerieAdapter() : PagedListAdapter<ResultSearch, SelectSerieAdapter.ViewHolder>(ResultSearch.DIFF_CALLBACK)   {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsSelectionBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
            val binding: MainCardsSelectionBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: ResultSearch?) = with(binding) {
            Glide.with(itemView.context)
                    .load(serie?.posterPath)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(cvImageCard)

            tvNameRecyclerView.text = serie?.name
            }
        }
}