package com.example.madeinbrasil.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.upcoming.Result

class MovieCreditsAdapter(
    private val castList: List<Cast>
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
        holder.bind(castList[position],{})
    }

    class ViewHolder(
        private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(cast: Cast, onMovieClicked: (Result?) -> Unit) = with(binding) {
            Log.i("casts","${cast?.profile_path}")
            Glide.with(itemView.context)
                .load(cast?.profile_path)
                .placeholder(R.drawable.made_in_brasil_logo)
                .into(cvImageActors)
            tvActorsName.text = cast?.name
            tvActorsCharacter.text = cast?.character

            itemView.setOnClickListener {
                //onMovieClicked(movie)
            }

        }

    }
}