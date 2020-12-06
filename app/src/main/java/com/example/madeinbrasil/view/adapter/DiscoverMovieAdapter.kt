package com.example.madeinbrasil.view.adapter

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R

import com.example.madeinbrasil.databinding.MainCardsMenuBinding
import com.example.madeinbrasil.model.discover.Result


import kotlinx.android.synthetic.main.filmsseries_popup.*

class DiscoverMovieAdapter(
        private val onMovieClicked: (Result?) -> Unit
): PagedListAdapter<Result, DiscoverMovieAdapter.ViewHolder>(Result.DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = MainCardsMenuBinding.inflate(layoutInflater, parent, false)
    return ViewHolder(binding)
}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClicked)
    }

    class ViewHolder(
            private val binding: MainCardsMenuBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(movie: Result?, onMovieClicked: (Result?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                    .load(movie?.poster_path)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(cvImageCardMenu)
            tvNameRecyclerViewMenu.text = movie?.title

            itemView.setOnClickListener {
                onMovieClicked(movie)
            }

            itemView.setOnLongClickListener {
                val dialog = Dialog(it.context)
                dialog.setContentView(R.layout.filmsseries_popup)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                Glide.with(it.context)
                        .load(movie?.poster_path)
                        .placeholder(R.drawable.made_in_brasil_logo)
                        .into(dialog.ivDialogPoster)

                dialog.tvDialogName.text = movie?.title
                dialog.cbShare.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND

                        putExtra(Intent.EXTRA_TEXT, "Filme: ${movie?.title} by MadeInBrasil")
                        type = "text/plain"

                        putExtra(Intent.EXTRA_STREAM, Uri.parse(movie?.title))
                        type = "image/*"

                        putExtra(
                                Intent.EXTRA_TITLE,
                                "Filme: ${movie?.title} \nShared by MadeInBrasil"
                        )
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Compartilhamento de Filmes")
                    ContextCompat.startActivity(it.context, shareIntent, null)

                }
                dialog.show()

                return@setOnLongClickListener true
            }
        }
        }
    }

