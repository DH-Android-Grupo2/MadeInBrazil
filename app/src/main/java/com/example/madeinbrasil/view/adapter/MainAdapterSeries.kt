package com.example.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.model.`class`.Series

class MainAdapterSeries(
    private val seriesList: List<Series>
): RecyclerView.Adapter<ViewHolderSeries>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSeries {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.main_cards, parent, false)
        return ViewHolderSeries(view)
    }

    override fun getItemCount(): Int {
        return seriesList.size
    }

    override fun onBindViewHolder(holder: ViewHolderSeries, position: Int) {
        holder.bind(seriesList[position])
    }
}