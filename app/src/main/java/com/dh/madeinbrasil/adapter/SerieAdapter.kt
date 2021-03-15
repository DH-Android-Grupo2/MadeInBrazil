package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.MainCardsBinding
import com.dh.madeinbrasil.model.search.ResultSearch
import kotlinx.android.synthetic.main.filmsseries_popup.*

class SerieAdapter(
    private val onSerieClicked: (ResultSearch?) -> Unit,
    private val onSerieLongClicked: (ResultSearch?) -> Unit
):PagedListAdapter<ResultSearch, SerieAdapter.ViewHolder>(ResultSearch.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onSerieClicked, onSerieLongClicked)
    }

    class ViewHolder(
        val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: ResultSearch?, onSerieClicked: (ResultSearch?) -> Unit, onSerieLongClicked: (ResultSearch?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load(serie?.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo_made_in_brasil)
                .into(cvImageCard)

            tvNameMedia.text = serie?.name

            itemView.setOnClickListener {
                onSerieClicked(serie)
            }

            itemView.setOnLongClickListener {
                onSerieLongClicked(serie)
                return@setOnLongClickListener true
            }
        }
    }
}