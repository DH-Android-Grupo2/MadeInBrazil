package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCardsMenuBinding
import com.dh.madeinbrasil.model.search.ResultSearch

import kotlinx.android.synthetic.main.filmsseries_popup.*

class DiscoverTvAdapter (
        private val onSerieClicked: (ResultSearch?, ImageView?) -> Unit,
        private val onSerieLongClicked: (ResultSearch?) -> Unit
) : PagedListAdapter<ResultSearch, DiscoverTvAdapter.ViewHolder>(ResultSearch.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsMenuBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onSerieClicked, onSerieLongClicked)
    }

    class ViewHolder(
            private val binding: MainCardsMenuBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(serie: ResultSearch?, onSerieClicked: (ResultSearch?, ImageView?) -> Unit, onSerieLongClicked: (ResultSearch?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                    .load(serie?.posterPath)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .into(cvImageCardMenu)
            tvNameRecyclerViewMenu.text = serie?.name

            itemView.setOnClickListener {
                onSerieClicked(serie, cvImageCardMenu)
            }

            itemView.setOnLongClickListener {
                onSerieLongClicked(serie)
                return@setOnLongClickListener true
            }
        }

    }
}