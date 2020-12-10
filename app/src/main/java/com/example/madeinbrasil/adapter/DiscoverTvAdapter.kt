package com.example.madeinbrasil.adapter

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainCardsMenuBinding
import com.example.madeinbrasil.model.discoverTV.Result
import com.example.madeinbrasil.model.search.ResultSearch

import kotlinx.android.synthetic.main.filmsseries_popup.*

class DiscoverTvAdapter(
        private val onSerieClicked: (ResultSearch?) -> Unit
) : PagedListAdapter<ResultSearch, DiscoverTvAdapter.ViewHolder>(ResultSearch.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsMenuBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onSerieClicked)
    }

    class ViewHolder(
            private val binding: MainCardsMenuBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(serie: ResultSearch?, onSerieClicked: (ResultSearch?) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                    .load(serie?.posterPath)
                    .placeholder(R.drawable.made_in_brasil_logo)
                    .into(cvImageCardMenu)
            tvNameRecyclerViewMenu.text = serie?.name

            itemView.setOnClickListener {
                onSerieClicked(serie)
            }

            itemView.setOnLongClickListener {
                val dialog = Dialog(it.context)
                dialog.setContentView(R.layout.filmsseries_popup)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                Glide.with(it.context)
                        .load(serie?.posterPath)
                        .placeholder(R.drawable.made_in_brasil_logo)
                        .into(dialog.ivDialogPoster)

                dialog.tvDialogName.text = serie?.name
                dialog.cbShare.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND

                        putExtra(Intent.EXTRA_TEXT, "Série: ${serie?.name} by MadeInBrasil")
                        type = "text/plain"

                        putExtra(Intent.EXTRA_STREAM, Uri.parse(serie?.name))
                        type = "image/*"

                        putExtra(
                                Intent.EXTRA_TITLE,
                                "Filme: ${serie?.name} \nShared by MadeInBrasil"
                        )
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Compartilhamento de Séries")
                    ContextCompat.startActivity(it.context, shareIntent, null)

                }
                dialog.show()

                return@setOnLongClickListener true
            }
        }

    }


}