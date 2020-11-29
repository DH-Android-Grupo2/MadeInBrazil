package com.example.madeinbrasil.adapter

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.model.upcoming.Result
import kotlinx.android.synthetic.main.filmsseries_popup.*

class FilmsAdapter(
    private val onMovieClicked: (Result?) -> Unit
) : PagedListAdapter<Result, FilmsAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClicked)
    }

    class ViewHolder(
        private val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(movie: Result?, onMovieClicked: (Result?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load(movie?.posterPath)
                .placeholder(R.drawable.made_in_brasil_logo)
                .into(cvImageCard)

            tvNameRecyclerView.text = movie?.title

            itemView.setOnClickListener {
                onMovieClicked(movie)
            }

            itemView.setOnLongClickListener {
                val dialog = Dialog(it.context)
                dialog.setContentView(R.layout.filmsseries_popup)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                Glide.with(it.context)
                    .load(movie?.posterPath)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(dialog.ivDialogPoster)

                dialog.tvDialogName.text = movie?.title
                dialog.show()

                return@setOnLongClickListener true
            }
        }

    }

}