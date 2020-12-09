package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.model.search.ResultSearch
import kotlinx.android.synthetic.main.filmsseries_popup.*

class SerieAdapter(
    private val onSerieClicked: (ResultSearch?) -> Unit
):PagedListAdapter<ResultSearch, SerieAdapter.ViewHolder>(ResultSearch.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onSerieClicked)
    }

    class ViewHolder(
        val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: ResultSearch?, onSerieClicked: (ResultSearch?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load(serie?.posterPath)
                .placeholder(R.drawable.made_in_brasil_logo)
                .into(cvImageCard)

            tvNameRecyclerView.text = serie?.name

            itemView.setOnClickListener {
                onSerieClicked(serie)
            }

            itemView.setOnLongClickListener {
                val dialog = android.app.Dialog(it.context)
                dialog.setContentView(R.layout.filmsseries_popup)
                dialog.window?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))

                Glide.with(it.context)
                    .load(serie?.posterPath)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(dialog.ivDialogPoster)

                dialog.tvDialogName.text = serie?.name
                dialog.show()

                return@setOnLongClickListener true
            }
        }
    }
}