package com.example.madeinbrasil.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.view.activity.FilmsAndSeriesActivity
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import kotlinx.android.synthetic.main.filmsseries_popup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class FilmsAdapter(
    private val onMovieClicked: (Result?) -> Unit,
    private val onMovieLongClicked: (Result?) -> Unit
) : PagedListAdapter<Result, FilmsAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClicked, onMovieLongClicked)
    }

    class ViewHolder(
        private val binding: MainCardsBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(movie: Result?, onMovieClicked: (Result?) -> Unit, onMovieLongClicked: (Result?) -> Unit) = with(binding) {

            Glide.with(itemView.context)
                .load(movie?.posterPath)
                .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cvImageCard)

            tvNameMedia.text = movie?.title

            itemView.setOnClickListener {
                onMovieClicked(movie)
            }

            itemView.setOnLongClickListener {
                onMovieLongClicked(movie)
                return@setOnLongClickListener true
            }
        }

    }

}