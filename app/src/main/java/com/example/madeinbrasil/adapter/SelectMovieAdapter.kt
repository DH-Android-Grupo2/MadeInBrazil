package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.databinding.MainCardsSelectionBinding

class SelectMovieAdapter(private var selectedItems: MutableList<Long>): PagedListAdapter<Result, SelectMovieAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    var onItemClick: ((Result) -> Unit)? = null

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
                onItemClick?.invoke(movie)
                tooglePosition(movie)
                notifyItemChanged(position)
            }
        }
    }

    private fun tooglePosition(movie: Result?) {
        movie?.let {
            if (selectedItems.contains(it.id.toLong()))
                selectedItems.remove(it.id.toLong())
            else
                selectedItems.add(it.id.toLong())
        }
    }

    class ViewHolder(
        private val binding: MainCardsSelectionBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(movie: Result?, selectedItems: List<Long>) = with(binding) {
            movie?.let {

                Glide.with(itemView.context)
                        .load(movie.posterPath)
                        .placeholder(R.drawable.logo_made_in_brasil)
                        .into(cvImageCard)

                tvNameRecyclerView.text = movie.title

                if(selectedItems.contains(movie.id.toLong())) {
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