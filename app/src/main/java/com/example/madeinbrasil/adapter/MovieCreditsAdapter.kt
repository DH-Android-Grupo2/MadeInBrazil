package com.example.madeinbrasil.adapter

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsMenuBinding
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.movieCredits.MovieCredits
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.view.adapter.ViewHolderActors
import kotlinx.android.synthetic.main.filmsseries_popup.*
import kotlinx.android.synthetic.main.main_cast_recyclerview.view.*

class MovieCreditsAdapter(
    private var castList: List<Cast> = listOf(),
    private val onMovieClicked: (Cast?) -> Unit
): RecyclerView.Adapter<MovieCreditsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return MovieCreditsAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return castList.size
    }
    override fun onBindViewHolder(holder: MovieCreditsAdapter.ViewHolder, position: Int) {
        holder.bind(castList[position],onMovieClicked)
    }

    class ViewHolder(
        private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(cast: Cast?, onMovieClicked: (Cast?) -> Unit) = with(binding) {
            cast?.profile_path = cast?.profile_path?.getFullImagePath()
            Glide.with(itemView.context)
                .load(cast?.profile_path)
                .placeholder(R.drawable.made_in_brasil_logo)
                .into(cvImageActors)
            tvActorsName.text = cast?.name
            tvActorsCharacter.text = cast?.character

            itemView.setOnClickListener {
                onMovieClicked(cast)
            }

        }

    }
}