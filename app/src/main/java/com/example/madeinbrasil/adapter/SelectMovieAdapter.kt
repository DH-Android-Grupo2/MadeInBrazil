package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.databinding.MainCardsSelectionBinding

class SelectMovieAdapter(private var selectedItems: MutableList<Int>): PagedListAdapter<Result, SelectMovieAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    var onItemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsSelectionBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)

        movie?.let {
            holder.bind(movie, selectedItems)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(movie.id)
                tooglePosition(movie)
                notifyItemChanged(position)
            }
        }
    }

    private fun tooglePosition(movie: Result?) {
        movie?.let {
            if (selectedItems.contains(it.id))
                selectedItems.remove(it.id)
            else
                selectedItems.add(it.id)
        }
    }

    class ViewHolder(
        private val binding: MainCardsSelectionBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(movie: Result?, selectedItems: List<Int>) = with(binding) {
            movie?.let {

                Glide.with(itemView.context)
                        .load(movie.posterPath)
                        .placeholder(R.drawable.made_in_brasil_logo)
                        .into(cvImageCard)

                tvNameRecyclerView.text = movie.title

                if(selectedItems.contains(it.id))
                    tvSelectionCover.setImageResource(R.drawable.remove_item)
                else
                    tvSelectionCover.setImageResource(R.drawable.add_item)
            }
        }
    }
}