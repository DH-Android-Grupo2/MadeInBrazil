package com.dh.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.model.movieCredits.Cast

class MainAdapterActors(
    private val actorsList: List<Cast>
): RecyclerView.Adapter<ViewHolderActors>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderActors {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_cast_recyclerview, parent, false)
        return ViewHolderActors(view)
    }

    override fun getItemCount(): Int {
        return actorsList.size
    }

    override fun onBindViewHolder(holder: ViewHolderActors, position: Int) {
        holder.bind(actorsList[position])
    }
}