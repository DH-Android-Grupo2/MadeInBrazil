package com.example.madeinbrasil.adapter

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityUserBinding
import com.example.madeinbrasil.databinding.FilmsseriesPopupBinding
import com.example.madeinbrasil.databinding.UserFavoritesRecyclerviewBinding
import com.example.madeinbrasil.model.upcoming.Result
import kotlinx.android.synthetic.main.filmsseries_popup.*


class HomeAdapter(
    private val onMovieClicked: (Result?) -> Unit
) : PagedListAdapter<Result, HomeAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UserFavoritesRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClicked)
    }

    class ViewHolder(
        private val binding: UserFavoritesRecyclerviewBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(movie: Result?, onMovieClicked: (Result?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load(movie?.posterPath)
                .placeholder(R.drawable.made_in_brasil_logo)
                .into(cvImageFavoritesProfile)
            tvNameFavoritesProfile.text = movie?.title

            itemView.setOnClickListener {
                onMovieClicked(movie)
            }

            itemView.setOnLongClickListener {
                val dialog:Dialog = Dialog(it.context)
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