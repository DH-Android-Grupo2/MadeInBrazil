package com.example.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.`class`.Films

class MainAdapterFilm(
    private var filmsList: List<Films>,
    private var filmPosition: (Int) -> Unit): RecyclerView.Adapter<ViewHolderFilm>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFilm {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.main_cards, parent, false)
        return ViewHolderFilm(view)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    override fun onBindViewHolder(holder: ViewHolderFilm, position: Int) {
        holder.bind(filmsList[position], filmPosition)
    }

}