package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.madeinbrasil.R
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.databinding.MainCastRecyclerviewBinding

class MidiaCastAdapter (
        private var castList: List<CastFirebase> = listOf()
): RecyclerView.Adapter<MidiaCastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCastRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    class ViewHolder(
            private val binding: MainCastRecyclerviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: CastFirebase) = with(binding) {
            Glide.with(itemView.context)
                    .load(cast.profilePath)
                    .placeholder(R.drawable.logo_made_in_brasil)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvImageActors)
            tvActorsName.text = cast.name
        }
    }
}